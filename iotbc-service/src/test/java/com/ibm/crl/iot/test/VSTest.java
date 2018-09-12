package com.ibm.crl.iot.test;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.AmazonS3Exception;
import com.ibm.cloud.objectstorage.services.s3.model.S3Object;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectInputStream;
import com.ibm.crl.mv.model.AssetDocs;
import com.ibm.crl.mv.model.AssetResult;
import com.ibm.crl.mv.model.VSDoc;
import com.ibm.crl.mv.model.VSRespResult;
import com.ibm.crl.mv.model.VSRespResult.VSResStatus;
import com.ibm.crl.mv.model.VSResult;
import com.ibm.crl.mv.utils.HttpRequestUtil;
//import com.ibm.crl.mv.utils.MD5Util;
import com.ibm.crl.mv.utils.MD5Util;

public class VSTest {
	private static ObjectMapper jsonOm = new ObjectMapper(new JsonFactory());
	private static AmazonS3 _cos;
	
	public static void main(String[] args) {
		
		String hash_b = gethash_b("doc.txt");
		System.out.println("hash_b =   "+hash_b);
		
		
//		//composer测试
//		String id = "6f5125ae-24f5-4758-830c-d8add1f9c758";
//		System.out.println("composer 结果：\n" +composerTest(id));
//		
//		//结果测试 
//		ObjectMapper oMapper = new ObjectMapper();
//		 try {
//			System.out.println("rsult 结果:\n"+oMapper.writeValueAsString(vsResult(id)));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
	public static String gethash_b (String filename) {
		String bucketName = "mes";
		String api_key = "6baWsh_GTfduFbZkPCNw-jhFhUaRUW6NA8MXQNefdgj0";
		String service_instance_id = "crn:v1:bluemix:public:cloud-object-storage:global:a/5c134980da641933383fba31b985b6fa:3978c790-3049-4543-b194-8359f8fb5bad::";
		String endpoint_url = "https://s3-api.us-geo.objectstorage.softlayer.net";
		String location = "us";
		_cos = createClient(api_key, service_instance_id, endpoint_url, location);
		S3Object s3Object = _cos.getObject(bucketName, filename);
		S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
		System.out.println( "code = "+s3Object.getKey());
		String md = MD5Util.getStreamMD5(s3ObjectInputStream);
		return md;
	}
	
	
	
	public static AmazonS3 createClient(String api_key, String service_instance_id, String endpoint_url,
			String location) {
		AWSCredentials credentials;
		if (endpoint_url.contains("objectstorage.softlayer.net")) {
			credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);
		} else {
			String access_key = api_key;
			String secret_key = service_instance_id;
			credentials = new BasicAWSCredentials(access_key, secret_key);
		}
		ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
		clientConfig.setUseTcpKeepAlive(true);

		AmazonS3 cos = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withEndpointConfiguration(new EndpointConfiguration(endpoint_url, location))
				.withPathStyleAccessEnabled(true).withClientConfiguration(clientConfig).build();
		return cos;
	}
	
	
	public static String composerTest(String id) {
		String url = "http://9.186.104.215:3000/api/org.poc.maximo.example.Sr/";
		try {
			 HttpResponse response = HttpRequestUtil.sendGet(url, id);//HttpRequestUtil.sendGet("http://9.186.104.215:3000/api/org.poc.maximo.example.Sr/",id);
			 if (response.getStatusLine().getStatusCode() == 200) {
				 String fine = EntityUtils.toString(response.getEntity(),"UTF-8");
				 return fine;
			 }else {
				return "请求失败";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}
	
	
	public static VSRespResult vsResult(String id) {
		VSRespResult vResult = new VSRespResult();
		String url = "http://9.186.104.215:3000/api/org.poc.maximo.example.Sr/";
		try {
			
				HttpResponse response = HttpRequestUtil.sendGet(url, id);
				if (response.getStatusLine().getStatusCode() == 200) {
					String fine = EntityUtils.toString(response.getEntity(),"UTF-8");
					
					System.out.println(fine);
					
					AssetResult aResult = jsonOm.readValue(fine, AssetResult.class);
					vResult.setResStatus(VSResStatus.Success);
					vResult.setMsg("Success  Get data from composer！");
					
					VSResult vsResult = new VSResult();
					vsResult.set$class(aResult.get$class());
					vsResult.setLong_description("Verify file contents！");
					vsResult.setStatus("Verified");
					vsResult.setTicketid(id);
					
					vResult.setVsResult(vsResult);
					List<AssetDocs>docs = aResult.getDocs();
					for (AssetDocs doc : docs) {
						VSDoc vsDoc = new VSDoc();
						String doc_url = doc.getDoc_url();
						String filename = doc_url.split("/mes/")[1];
						vsDoc.setFullName(filename);
						String hash_b =gethash_b(filename);
						String hash_a = doc.getHash();
						if (hash_a.equalsIgnoreCase(hash_b)) {
							vsDoc.setDescription("The file hash values are the same！");
							vsDoc.setValidation(true);
						}else {
							vsDoc.setDescription("The file hash values are different！");
							vsDoc.setValidation(false);
						}
						
						vResult.getVsResult().getvDocs().add(vsDoc);
					}
					
					return vResult;
				}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			vResult.setResStatus(VSResStatus.Fail);
			vResult.setMsg("Composer service isn't established!!!");
			vResult.setVsResult(null);
			return vResult;
		}
		
		
		return vResult;
	}
	
	

}
