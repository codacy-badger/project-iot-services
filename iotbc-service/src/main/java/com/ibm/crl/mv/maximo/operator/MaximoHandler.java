package com.ibm.crl.mv.maximo.operator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.ibm.cloud.objectstorage.SdkClientException;
//import com.ibm.cloud.objectstorage.services.s3.AmazonS3;

import com.ibm.crl.mv.maximo.model.AssetPostResult;
import com.ibm.crl.mv.maximo.model.AssetPostResult.SRResult;
import com.ibm.crl.mv.maximo.model.UpdateList.HistStatus;
import com.ibm.crl.mv.maximo.model.UpdateRep;
import com.ibm.crl.mv.maximo.model.UpdateReq;
import com.ibm.crl.mv.maximo.model.AssetRequest;

import com.ibm.crl.mv.maximo.model.FileModel;
import com.ibm.crl.mv.maximo.model.SRDocs;
import com.ibm.crl.mv.maximo.model.SRHistModel;
import com.ibm.crl.mv.maximo.model.SRModel;
import com.ibm.crl.mv.maximo.model.SaveFileModel;

import com.ibm.crl.mv.maximo.model.UpdateList;
//import com.ibm.crl.mv.operator.COSOperatorImpl;
//import com.ibm.crl.mv.utils.COSClient;
import com.ibm.crl.mv.utils.ConfigUtil;
import com.ibm.crl.mv.utils.HttpRequestUtil;
import com.ibm.crl.mv.utils.MD5Util;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference; 


public class MaximoHandler {
	
	private static MaximoHandler handler = null;
	private static Logger logger = LoggerFactory.getLogger(MaximoHandler.class);
	private static String Dir = ConfigUtil.getPersistDir();
	private static ObjectMapper jsonOm = new ObjectMapper(new JsonFactory()); 
	private String NOWDATE = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()); 
	private Date NOWTIME = new Date();
	
	private static String ASSET_SR = "org.poc.maximo.SR";
	private static String ASSET_SR_DOC = "org.poc.maximo.Docs";
	private static String ASSET_SR_HIST = "org.poc.maximo.SR_HIST";
	private static String UPDATE_EVENT = "org.poc.maximo.InspectionUpdateEvent";
	
	private String srUrl = "http://localhost:3000/api/SR";
	private String updateUrl = "http://localhost:3000/api/InspectionUpdateEvent";
	private String srhistUrl = "http://localhost:3000/api/SR_HIST"; 
	
	
	//实例化
	public static MaximoHandler getInstance() {
		
		if (handler == null) {
			handler = new MaximoHandler();
		}
		return handler;
	}
	
	
	/** post asset max*/
	
	public AssetPostResult postSR(AssetRequest aRequest){
		
		AssetPostResult rr = new AssetPostResult();
//		if (rr.getsResult().equals(SRResult.Fail)) {
//			return rr;
//		}
//		
		if (StringUtils.isEmpty(aRequest.getAsset_number()) || StringUtils.isEmpty(aRequest.getDes()) 
			|| StringUtils.isEmpty(aRequest.getLocation()) || StringUtils.isEmpty(aRequest.getLongDes())
			|| StringUtils.isEmpty(aRequest.getSrtype()) || StringUtils.isEmpty(aRequest.getStatus())
			|| aRequest.getFileList().isEmpty() || StringUtils.isEmpty(aRequest.getReportDate())) {
			rr.setMeg("here is  the field of null value in request body");
			rr.setsResult(SRResult.Fail);
			rr.setsModel(null);
			return rr;
		}
		
		//post sr
		SRModel aSr = new SRModel();
		//post transaction
		UpdateReq tReq = new UpdateReq();//上传 
		//post sr_hsit
		SRHistModel aSrHist = new SRHistModel();
		 
		aSr.set$class(ASSET_SR);
		aSr.setTicketid("SR-" + aRequest.getAsset_number()+"_"+ NOWDATE + (int)((Math.random()*9+1)*100000));//SR-1306_2018-09-03
		aSr.setSrtype(aRequest.getSrtype());
		aSr.setActualstart(NOWDATE);
		aSr.setReportdate(NOWDATE);
		aSr.setDescription(aRequest.getDes());
		aSr.setDescription_longdescription(aRequest.getLongDes());
		aSr.setLocation(aRequest.getLocation());
		aSr.setStatus(aRequest.getStatus());
		aSr.setStatusDescription(aRequest.getStatus());
		
		List<FileModel> fModels = aRequest.getFileList();
		fModels.forEach(fm -> {
			
			byte[] by = Base64.decodeBase64(fm.getFileBinary());
			String md5 = MD5Util.getFileMD5(by);
			
			SaveFileModel saveFModel = new SaveFileModel();
			saveFModel.setFileName(aSr.getTicketid()+"_"+fm.getFileName());
			saveFModel.setFileBinary(by);
			try {
				String file_href = saveFile(saveFModel);
				
				SRDocs docs = new SRDocs();
				docs.set$class(ASSET_SR_DOC);
				docs.setFileHref(file_href);
				docs.setFileName(saveFModel.getFileName());
				docs.setRecordId(UUID.randomUUID().toString());
				docs.setHash(md5);
				aSr.getDocs().add(docs);	
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		try {
			String srParam = jsonOm.writeValueAsString(aSr);
			HttpResponse response = HttpRequestUtil.sendPost(srUrl, srParam);
			
			//上传 sr
			if (response.getStatusLine().getStatusCode() == 200) {
				String fine = EntityUtils.toString(response.getEntity(), "UTF-8");
				logger.info("----- sr response from composer --------:\n" + fine);
				SRModel srRep = jsonOm.readValue(fine, SRModel.class); 
				
				tReq.set$class(UPDATE_EVENT);
				tReq.setInspection(srRep);
				tReq.setTimestamp(NOWTIME);
				String updateParam = jsonOm.writeValueAsString(tReq);
				
				
				HttpResponse response2 = HttpRequestUtil.sendPost(updateUrl, updateParam);
				//上传updateEvent
				if (response2.getStatusLine().getStatusCode() == 200) {
					String fine2 = EntityUtils.toString(response2.getEntity(), "UTF-8");
					logger.info("----- updateEvent response from composer --------:\n" + fine2);
					UpdateRep tRep = jsonOm.readValue(fine2, UpdateRep.class);
					aSrHist.set$class(ASSET_SR_HIST);
					aSrHist.setTicketidTime(aRequest.getAsset_number()+"_"+NOWDATE + (int)((Math.random()*9+1)*100000));//
					aSrHist.setEvt(tRep);
					String histParam = jsonOm.writeValueAsString(aSrHist);
					
					HttpResponse response3 = HttpRequestUtil.sendPost(srhistUrl, histParam);
					//上传sr Hist
					if (response3.getStatusLine().getStatusCode() == 200) {
						String fine3 = EntityUtils.toString(response3.getEntity(), "UTF-8");
						logger.info("----- sr hist response from composer --------:\n" + fine3);
						SRHistModel sModel = jsonOm.readValue(fine3, SRHistModel.class);
						rr.setMeg("Successfully post data to blockchain！！！");
						rr.setsResult(SRResult.Success);
						rr.setsModel(sModel);
						return rr;
					}else {
						String error = EntityUtils.toString(response3.getEntity(), "UTF-8");
						return extracted(rr, "sr_hist error: "+error);
					}
					
				}else {
					String error = EntityUtils.toString(response2.getEntity(), "UTF-8");
					return extracted(rr, "update_event error: "+error);
				}
				
			}else {
				String error = EntityUtils.toString(response.getEntity(), "UTF-8");
				return extracted(rr, "sr error: "+error);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			String msg = "Composer service isn't established!!!";
			e.printStackTrace();
			return extracted(rr, msg);
			
		}
		
	}
	
	
	/**get update list*/
	public UpdateList getSrHist(Boolean download) {
		UpdateList uList = new UpdateList();
		try {
			String param = "";
			HttpResponse response = HttpRequestUtil.sendGet(srhistUrl, param);
			if (response.getStatusLine().getStatusCode() == 200) {
				String fine = EntityUtils.toString(response.getEntity(), "UTF-8");
				List<SRHistModel> list = jsonOm.readValue(fine, new TypeReference<List<SRHistModel>>() {
				});
				
				uList.sethStatus(HistStatus.Success);
				uList.setDownload(download);
				uList.setMsg("get sr hist list success");
				uList.setuList(list);
				return uList;
				
			}else {
				String error = EntityUtils.toString(response.getEntity(), "UTF-8");
				return extracted(uList, error);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String error = "Composer service isn't established!!!";
			e.printStackTrace();
			return extracted(uList, error);
		}
	}
	

	/**post请求错误结果*/
	private AssetPostResult extracted(AssetPostResult rr, String error) {
		rr.setsResult(SRResult.Fail);
		rr.setMeg(error);
		rr.setsModel(null);
		logger.info("----------post meg to composer error:------------\n" + error);
		return rr;
	}
	
	/**get 请求错误结果*/
	private UpdateList extracted(UpdateList uList, String error) {
		uList.sethStatus(HistStatus.Fail);
		uList.setDownload(false);
		uList.setMsg("ERROR:  "+error);
		uList.setuList(new ArrayList<>());
		logger.info("----------get meg to composer error:------------/n" + error);
		return uList;
	}
	
	/**把文件保存到file—server 并且上传到upload
	 * @throws IOException */ 
	private String saveFile(SaveFileModel file) throws IOException{
		File persistF = new File(Dir + File.separator + file.getFileName());
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(persistF))) {
			bos.write(file.getFileBinary(), 0, file.getFileBinary().length);
			bos.flush();
		} catch (IOException e) {
			throw e;
		}
		file.setFilePath(persistF.getPath());
		String file_path = file.getFilePath();
		logger.info("File : " + file.getFileName() + ", persisted");
		return file_path; //上传到object-store
		//return "http://baidu.com";
	}
	
	
	/**把文件保存到 objectstore*/
//	private String uploadToCOS(String persistPath)throws SdkClientException {
//		AmazonS3 _cos = COSClient.getClient();
//		String bucket = ConfigUtil.getProperties("bucket");
//		String url = COSOperatorImpl.getInstance().multiPartUpload(_cos, bucket, persistPath);
//		return url;
//	}
	
}
