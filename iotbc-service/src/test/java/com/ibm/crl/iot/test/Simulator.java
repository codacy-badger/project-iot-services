//package com.ibm.crl.iot.test;
//
//import java.io.IOException;
//import java.util.UUID;
//
//import org.apache.http.ParseException;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.wink.client.Resource;
//import org.apache.wink.client.RestClient;
//
//import com.google.gson.Gson;
//import com.ibm.crl.mv.model.BodyDescriptor;
//import com.ibm.crl.mv.model.DocDescriptor;
//import com.ibm.crl.mv.utils.Base64Utils;
//
//public class Simulator {
//
//	public static void main(String[] args) {
//
//		
//		post();
//	}
//
//	public static void post() {
//
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		CloseableHttpResponse response = null;
//
//		String url = "http://localhost:8088/iotbc-service/api/1/mes/";
//		HttpPost httpPost = new HttpPost(url);
//		
////		httpPost.setHeader("Content-Type", "application/json");
//		
//		
//		BodyDescriptor body = new BodyDescriptor();
//
//		body.setOrgClass("org.poc.maximo.example.Sr");
//		body.setLong_description("test description");
//		body.setStatus("PENDING");
//		body.setTicketid(UUID.randomUUID().toString());
//
//		DocDescriptor doc = new DocDescriptor();
//
//		doc.setDescription("test file");
//
//		String file = "C:\\private\\link.txt";
//
//		java.util.Optional<String> optional = Base64Utils.encodeFileToString(file);
//
//		if (!optional.isPresent()) {
//			System.out.println("Failed to encode");
//			return;
//		}
//
//		String str = optional.get();
//		doc.setDocBinary(str);
//		doc.setFullName("link.txt");
//		
//		
//		body.getDocs().add(doc);
//		
//		Gson gson = new Gson();
//		String req = gson.toJson(body);
//		
//		System.out.println(req);
//		
//		try {
//			String result = null;
//			httpPost.setEntity(new StringEntity(req));
//			response = httpclient.execute(httpPost);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				result = EntityUtils.toString(response.getEntity(), "UTF-8");
//			}
//			
//			System.out.println(result);
//			
//		} catch (ParseException | IOException e) {
//			 e.printStackTrace();
//
//		}
//
//	}
//
//	public static void get() {
//
//		RestClient client = new RestClient();
//
//		Resource resource = client.resource("http://services.com/HelloWorld");
//
//		String response = resource.accept("text/plain").get(String.class);
//
//	}
//}
