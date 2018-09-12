package com.ibm.crl.iot.test;


import java.io.File;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;


import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.AbortMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.CompleteMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.DeleteBucketRequest;
import com.ibm.cloud.objectstorage.services.s3.model.InitiateMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.InitiateMultipartUploadResult;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.PartETag;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import com.ibm.cloud.objectstorage.services.s3.model.UploadPartRequest;
import com.ibm.cloud.objectstorage.services.s3.model.UploadPartResult;

public class COSTest {

	private static AmazonS3 _cos;

	/**
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args) throws UnsupportedEncodingException{

		// SDKGlobalConfiguration.IAM_ENDPOINT = "https://iam.bluemix.net/oidc/token";

		String bucketName = "mes";
		String api_key = "6baWsh_GTfduFbZkPCNw-jhFhUaRUW6NA8MXQNefdgj0";
		String service_instance_id = "crn:v1:bluemix:public:cloud-object-storage:global:a/5c134980da641933383fba31b985b6fa:3978c790-3049-4543-b194-8359f8fb5bad::";
		String endpoint_url = "https://s3-api.us-geo.objectstorage.softlayer.net";
		String location = "us";
		String demotime = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Current time: " +demotime);
		_cos = createClient(api_key, service_instance_id, endpoint_url, location);
		
		
		//AmazonS3 _cos = COSClient.getClient();
		
		
//		delAllObjects(bucketName, _cos);
//		delBucket(bucketName, _cos);

//		_cos.deleteBucket(bucketName);
//		 boolean exists = _cos.doesBucketExist(bucketName);
//		 System.out.println(exists);

		// listBuckets(_cos);

		// create standard client
		
//	_cos.createBucket(bucketName);
	

//
		listObjects(bucketName, _cos);
		
		
		
		
//		ByteArrayOutputStream  baos  = null;
//		try {
//			
//			 baos = new ByteArrayOutputStream();
//			
//			
////			byte[] buff = new byte[s3ObjectInputStream.available()];
//			
//			byte[] bytebuff = new byte[8192];
//			
////			int pos = 0;
//			int len = 0;
//			
//			while((len = s3ObjectInputStream.read(bytebuff)) != -1) {
//				
//				baos.write(bytebuff, 0, len);
//				
////				System.arraycopy(bytebuff, 0,buff,  pos, len);
//				
//			}
//			
//			
//			byte[] buff = baos.toByteArray();
//			
//			String md5 = MD5Util.getFileMD5(buff);
//			
//			
//			
//         System.out.println(md5);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}finally {
//			
//			try {
//				if(baos != null)
//					baos.close();
//				
//				if(s3ObjectInputStream != null)
//					s3ObjectInputStream.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		
		
		 
//		String content = _cos.getObjectAsString(bucketName, "test1.png");
//		
//		StringReader reader = new StringReader(content);
		
//		try {
//			7f0a8a284d6224aeaec10bcb1761e55f
//			byte[] buff = IOUtils.toByteArray(reader);
//			
//			String md5 = MD5Util.getFileMD5(buff);
//			
//			String file = "/Users/Jinhui/Desktop/test1.png";
//			
//			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
//			
//			byte[] bytebuff = new byte[(int)randomAccessFile.length()];
//			randomAccessFile.readFully(bytebuff);
//			
//			String md5f = MD5Util.getFileMD5(bytebuff);
//			
//			System.out.println(String.format("md51 : %s /n  md52 : %s ", md5,""));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		
//		System.out.println(getEncoding(content));
//		System.out.println("content:  " + content);
		
//		 1. decode binary data
//					byte[] bytebuff = Base64.decodeBase64(dd.getDocBinary());
//					rdd.setBinaryContent(bytebuff);
//
//					String md5 = MD5Util.getFileMD5(rdd.getBinaryContent());
		
//		Base64.Decoder decoder = Base64.getDecoder();
//		Base64.Encoder encoder = Base64.getEncoder();
//		//编码
//		byte[] textByte = content.getBytes("UTF-8");
//		String encodedText = encoder.encodeToString(textByte);
//		System.out.println(content);
		
		
//		try {
//			
//			System.out.println("base64 ------------- \n:  " + end);
//			String hash_b = MD5Util.getStringMD5(content);
//			System.out.println("hash:  " + hash_b);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
		//}
		
		
		
		
		 //VSRespResult respResult = DocHandler.getInstance().validation("org.poc.maximo.example.Sr", "6f5125ae-24f5-4758-830c-d8add1f9c758", false);
		 
		 //测试成功
//		 try {
//			 
//			 HttpResponse response = HttpRequestUtil.sendGet("http://9.186.104.215:3000/api/org.poc.maximo.example.Sr/", "6f5125ae-24f5-4758-830c-d8add1f9c758");
//			 if (response.getStatusLine().getStatusCode() == 200) {
//				 String fine = EntityUtils.toString(response.getEntity(),"UTF-8");
//				 System.out.println(fine);
//			 }else {
//				System.out.println("xxxxxxxxxxxx");
//			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e);
//		}

	}
	
	
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   }  

	/**
	 * @param bucketName
	 * @param clientNum
	 * @param api_key
	 *            (or access key)
	 * @param service_instance_id
	 *            (or secret key)
	 * @param endpoint_url
	 * @param location
	 * @return AmazonS3
	 */
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

	public static void delAllObjects(String bucketName, AmazonS3 cos) {

		ObjectListing objectListing = cos.listObjects(new ListObjectsRequest().withBucketName(bucketName));
		objectListing.getObjectSummaries().forEach(s -> {

			cos.deleteObject(bucketName, s.getKey());
			System.out.println("Del  bucket : " + bucketName + " - file : " + s.getKey());
		});

	}

	public static void delBucket(String bucketName, AmazonS3 cos) {


		cos.deleteBucket(new DeleteBucketRequest(bucketName));
		System.out.println("Bucket : " + bucketName + ", deleted");
	}

	/**
	 * @param bucketName
	 * @param cos
	 */
	public static void listObjects(String bucketName, AmazonS3 cos) {
		System.out.println("Listing objects in bucket " + bucketName);
		ObjectListing objectListing = cos.listObjects(new ListObjectsRequest().withBucketName(bucketName));
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			System.out.println(objectSummary.getStorageClass());
			System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
		}
		System.out.println();
	}

	/**
	 * @param cos
	 */
	public static void listBuckets(AmazonS3 cos) {
		System.out.println("Listing buckets");
		final List<Bucket> bucketList = cos.listBuckets();
		for (final Bucket bucket : bucketList) {
			System.out.println(bucket.getName());
		}
		System.out.println();
		
		
		System.out.println( cos.listObjects("mes"));
		
	}

	public static void multiPartUpload(String bucket, String name, String filePath) {

		File file = new File(filePath);
		if (!file.isFile()) {
			System.out.printf("The file '%s' does not exist or is not accessible.\n", filePath);
			return;
		}

		System.out.printf("Starting multi-part upload for %s to bucket: %s\n", name, bucket);

		InitiateMultipartUploadResult mpResult = _cos
				.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucket, name));
		String uploadID = mpResult.getUploadId();

		// begin uploading the parts
		// min 5MB part size
		long partSize = 1024 * 1024 * 5;
		long fileSize = file.length();
		long partCount = ((long) Math.ceil(fileSize / partSize)) + 1;
		List<PartETag> dataPacks = new ArrayList<PartETag>();

		try {
			long position = 0;
			for (int partNum = 1; position < fileSize; partNum++) {
				partSize = Math.min(partSize, (fileSize - position));

				System.out.printf("Uploading to %s (part %s of %s)\n", name, partNum, partCount);

				UploadPartRequest upRequest = new UploadPartRequest().withBucketName(bucket).withKey(name)
						.withUploadId(uploadID).withPartNumber(partNum).withFileOffset(position).withFile(file)
						.withPartSize(partSize);

				UploadPartResult upResult = _cos.uploadPart(upRequest);
				dataPacks.add(upResult.getPartETag());

				position += partSize;
			}

			// complete upload
			_cos.completeMultipartUpload(new CompleteMultipartUploadRequest(bucket, name, uploadID, dataPacks));
		} catch (SdkClientException sdke) {
			_cos.abortMultipartUpload(new AbortMultipartUploadRequest(bucket, name, uploadID));
		}
	}

}
