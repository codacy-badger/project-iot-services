package com.ibm.crl.mv.operator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.S3Object;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectInputStream;
import com.ibm.crl.mv.constant.SysConstant;
import com.ibm.crl.mv.dao.MesDaoImpl;
import com.ibm.crl.mv.db.model.InsertOperator;
import com.ibm.crl.mv.db.model.MesDoc;
import com.ibm.crl.mv.db.model.MesJob;
import com.ibm.crl.mv.maximo.model.UpdateList;
import com.ibm.crl.mv.maximo.model.UpdateList.HistStatus;
import com.ibm.crl.mv.model.AssetDocs;
import com.ibm.crl.mv.model.AssetResult;
import com.ibm.crl.mv.model.BodyDescriptor;
import com.ibm.crl.mv.model.DocDescriptor;
import com.ibm.crl.mv.model.FabricErrResp;
import com.ibm.crl.mv.model.FabricReqDescriptor;
import com.ibm.crl.mv.model.FabricReqDoc;
import com.ibm.crl.mv.model.RespResult;
import com.ibm.crl.mv.model.RespResult.ResStatus;
import com.ibm.crl.mv.model.VSRespResult.VSResStatus;
import com.ibm.crl.mv.model.VSResult;
import com.ibm.crl.mv.model.RespResultInfo;
import com.ibm.crl.mv.model.RichDocDescriptor;
import com.ibm.crl.mv.model.VSDoc;
import com.ibm.crl.mv.model.VSRespResult;
import com.ibm.crl.mv.utils.COSClient;
import com.ibm.crl.mv.utils.ConfigUtil;
import com.ibm.crl.mv.utils.DateUtils;
import com.ibm.crl.mv.utils.HttpRequestUtil;
import com.ibm.crl.mv.utils.MD5Util;

public class DocHandler {

	private static Logger log = LoggerFactory.getLogger(DocHandler.class);

	private static String Dir = ConfigUtil.getPersistDir();

	private static String Composer_url = ConfigUtil.getProperties("composer_url");
	private static String Composer_url2 = ConfigUtil.getProperties("composer_url2");

	private static DocHandler handler = null;

	private static ObjectMapper jsonOm = new ObjectMapper(new JsonFactory());

	public static DocHandler getInstance() {

		if (handler == null) {
			handler = new DocHandler();
		}

		return handler;
	}
	
	
	public UpdateList getSrHist(Boolean download) {
		UpdateList uList = new UpdateList();
		
		uList.sethStatus(HistStatus.Fail);
		uList.setDownload(false);
		uList.setMsg("ERROR:  request api path, pls check-----!!!");
		uList.setuList(new ArrayList<>());
		log.info("----------get meg to composer error:------------/n");
		return uList;
	}
	
	public RespResult handle(BodyDescriptor descriptors, String record_ID) {

		RespResult rr = new RespResult();

		List<RichDocDescriptor> richList = new ArrayList<>();
		// process request body
		enhanceDocDescriptor(descriptors, richList, record_ID, rr);

		if (rr.getStatus().equals(ResStatus.Fail))
			return rr;

		if (richList.isEmpty()) {
			rr.setStatus(ResStatus.Fail);
			return rr;
		}

		// build composer request body and rdb data

		FabricReqDescriptor composerDescriptor = new FabricReqDescriptor();

		richList.forEach(rd -> {

			FabricReqDoc bumper = new FabricReqDoc();
			bumper.setDescription(rd.getDescription());
			bumper.setDocUrl(rd.getCosUrl());
			bumper.setHash(rd.getHashKey());
			bumper.setRecordId(rd.getRecordId());
			bumper.setCreateTime(DateUtils.getUTCTime());

			composerDescriptor.getDocs().add(bumper);

			RespResultInfo info = new RespResultInfo();

			info.setFilename(rd.getFullName());
			info.setHashKey(rd.getHashKey());

			rr.getFineResult().add(info);

		});

		// composerDescriptor.set$class(descriptors.get$class());
		composerDescriptor.setLongDescription(descriptors.getLongDescription());
		composerDescriptor.setStatus(descriptors.getStatus());
		composerDescriptor.setTicketid(descriptors.getTicketid());

		try {

			String param = jsonOm.writeValueAsString(composerDescriptor);

			HttpResponse response = HttpRequestUtil.sendPost(Composer_url, param);

			if (response.getStatusLine().getStatusCode() == 200) {

				String fine = EntityUtils.toString(response.getEntity(), "UTF-8");
				log.info(String.format("Hyperledger fabric composer response result : %s", fine));

			} else if (response.getStatusLine().getStatusCode() == 500) {

				String error = EntityUtils.toString(response.getEntity(), "UTF-8");

				FabricErrResp err = jsonOm.readValue(error, FabricErrResp.class);
				rr.setStatus(ResStatus.Fail);
				rr.setMsg(err.getError().getMessage());
				log.info(err.getError().getMessage());

				return rr;
			}
		} catch (IOException e) {
			String msg = "Composer service isn't established!!!";
			log.error(msg, e);
			rr.setStatus(ResStatus.Fail);
			rr.setMsg(msg);
			return rr;
		}

		// save to rdb
		try {
			saveDB(richList, record_ID);
		} catch (SQLException e) {

			String msg = "The same content file have been uploaded!";
			log.error(msg, e);
			rr.setStatus(ResStatus.Fail);
			rr.setMsg(msg);
			return rr;
		}

		rr.setRecordId(record_ID);

		return rr;
	}

	private void enhanceDocDescriptor(BodyDescriptor descriptors, List<RichDocDescriptor> richList, String record_ID,
			RespResult rr) {

		for (Iterator<DocDescriptor> it = descriptors.getDocs().iterator(); it.hasNext();) {

			DocDescriptor dd = it.next();

			RichDocDescriptor rdd = new RichDocDescriptor();

			// 1. decode binary data
			byte[] bytebuff = Base64.decodeBase64(dd.getDocBinary());
			rdd.setBinaryContent(bytebuff);

			String md5 = MD5Util.getFileMD5(rdd.getBinaryContent());

			rdd.setFullName(dd.getFullName());
			// check
			try {

				MesDoc doc = MesDaoImpl.getInstance().selectDocByHash(SysConstant.MD, md5);

				if (doc != null) {

					rdd.setFullName(record_ID + "_" + dd.getFullName());
					// String msg = String.format("File: %s, the same hash_Key : %s already exists
					// !!!", dd.getFullName(),md5);
					// log.info(msg);
					//
					// RespResultInfo respResultInfo = new RespResultInfo();
					// respResultInfo.setFilename(dd.getFullName());
					// respResultInfo.setHash_key(md5);
					// respResultInfo.setMsg(msg);
					// rr.getErrResult().add(respResultInfo);
					//
					// continue;
				}
			} catch (SQLException e1) {
				String msg = "Failed to query M_Doc by  hash_key : " + md5;
				log.error(msg, e1);
				continue;
			}

			rdd.setRecordId(record_ID);

			rdd.setDescription(dd.getDescription());

			rdd.setHashKey(md5);

			// 2.persist data to local disk
			try {
				persist(rdd);
			} catch (IOException e) {
				String msg = "Failed to save file!!!";
				log.error(msg, e);
				rr.setStatus(ResStatus.Fail);
				rr.setMsg(msg);
				return;
			}

			String cos_url = "";
			try {
				cos_url = uploadToCOS(rdd.getPersistPath());
			} catch (SdkClientException e) {
				// TODO: repeat
				String msg = "Failed to upload file to Cloud Object Store!!!";
				log.error(msg, e);
				rr.setStatus(ResStatus.Fail);
				rr.setMsg(msg);
				return;
			}

			rdd.setCosUrl(cos_url);

			richList.add(rdd);

		}

		descriptors.getDocs().clear();
	}

	private void persist(RichDocDescriptor desc) throws IOException {

		File persistF = new File(Dir + File.separator + desc.getFullName());

		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(persistF))) {

			bos.write(desc.getBinaryContent(), 0, desc.getBinaryContent().length);
			bos.flush();
		} catch (IOException e) {
			throw e;
		}

		desc.setPersistPath(persistF.getPath());
		log.info("File : " + desc.getFullName() + ", persisted");

	}

	private void saveDB(List<RichDocDescriptor> descSet, String record_ID) throws SQLException {

		List<Map<String, Object>> mesDocSet = new ArrayList<>();

		List<Map<String, Object>> mesJobSet = new ArrayList<>();

		List<InsertOperator> insertDatas = new ArrayList<>();

		String currentTime = DateUtils.getStringDate();

		MesJob job = new MesJob();
		job.setRecordID(record_ID);
		job.setCreateTime(currentTime);
		mesJobSet.add(job.describe());

		for (RichDocDescriptor ufd : descSet) {

			String fullName = ufd.getFullName();

			if (fullName.startsWith(ufd.getRecordId()))
				continue;

			MesDoc doc = new MesDoc();

			doc.setHashKey(ufd.getHashKey());
			doc.setDocURL(ufd.getCosUrl());
			doc.setRecordID(record_ID);
			doc.setCreateTime(currentTime);

			mesDocSet.add(doc.describe());
		}

		InsertOperator jobOperator = new InsertOperator();
		jobOperator.setRecords(mesJobSet);
		jobOperator.setTableName(SysConstant.MJ);

		insertDatas.add(jobOperator);

		if (mesDocSet.isEmpty())
			return;
		InsertOperator docOperator = new InsertOperator();
		docOperator.setRecords(mesDocSet);
		docOperator.setTableName(SysConstant.MD);

		insertDatas.add(docOperator);

		MesDaoImpl.getInstance().insertRecord(insertDatas);

	}

	private String uploadToCOS(String persistPath) throws SdkClientException {

		AmazonS3 _cos = COSClient.getClient();

		String bucket = ConfigUtil.getProperties("bucket");

		String url = COSOperatorImpl.getInstance().multiPartUpload(_cos, bucket, persistPath);

		delFile(persistPath);

		return url;
	}
	
	

	private String gethashB(String filename) {
		String bucketName = ConfigUtil.getProperties("bucket");
		// String api_key = "6baWsh_GTfduFbZkPCNw-jhFhUaRUW6NA8MXQNefdgj0";
		// String service_instance_id =
		// "crn:v1:bluemix:public:cloud-object-storage:global:a/5c134980da641933383fba31b985b6fa:3978c790-3049-4543-b194-8359f8fb5bad::";
		// String endpoint_url = "https://s3-api.us-geo.objectstorage.softlayer.net";
		// String location = "us";
		AmazonS3 _cos = COSClient.getClient();
		S3Object s3Object = _cos.getObject(bucketName, filename);
		S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
		String md = MD5Util.getStreamMD5(s3ObjectInputStream);
		return md;
	}

	private void printResult(VSRespResult result) {
		ObjectMapper oMapper = new ObjectMapper();
		try {
			System.out.println("rsult 结果:\n" + oMapper.writeValueAsString(result));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public VSRespResult validation(String asset, String id, Boolean download) {
		// http://9.186.104.215:3000/api/org.poc.maximo.example.Sr/6f5125ae-24f5-4758-830c-d8add1f9c758
		VSRespResult vResult = new VSRespResult();

		try {
			String param = "/" + asset + "/" + id;
			HttpResponse response = HttpRequestUtil.sendGet(Composer_url2, param);
			if (response.getStatusLine().getStatusCode() == 200) {
				String fine = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println("composer:  \n" + fine);
				System.out.println(fine);
				AssetResult aResult = jsonOm.readValue(fine, AssetResult.class);
				vResult.setResStatus(VSResStatus.Success);
				vResult.setMsg("Success  Get data from composer！");
				VSResult vsResult = new VSResult();
				vsResult.set$class(aResult.get$class());
				vsResult.setLongDescription("Verify file contents！");
				vsResult.setStatus("Verified");
				vsResult.setTicketid(id);
				vResult.setVsResult(vsResult);
				List<AssetDocs> docs = aResult.getDocs();
				for (AssetDocs doc : docs) {
					VSDoc vsDoc = new VSDoc();
					String doc_url = doc.getDocUrl();
					String filename = doc_url.split("/mes/")[1];
					vsDoc.setFullName(filename);
					String hash_b = gethashB(filename);
					String hash_a = doc.getHash();
					if (hash_a.equalsIgnoreCase(hash_b)) {
						vsDoc.setDescription("The file hash values are the same！");
						vsDoc.setValidation(true);
					} else {
						vsDoc.setDescription("The file hash values are different！");
						vsDoc.setValidation(false);
					}

					vResult.getVsResult().getvDocs().add(vsDoc);
				}
				printResult(vResult);
				return vResult;
			}

		} catch (Exception e) {
			// TODO: handle exception
			vResult.setResStatus(VSResStatus.Fail);
			vResult.setMsg("Composer service isn't established!!!");
			vResult.setVsResult(null);
			printResult(vResult);
			return vResult;
		}

		return vResult;

	}

	public void delFile(String fileName) {

		File file = new File(fileName);
		file.delete();

		log.info(String.format("File :  %s , deleted", file.getName()));
	}

}
