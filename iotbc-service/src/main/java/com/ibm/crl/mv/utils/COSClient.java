package com.ibm.crl.mv.utils;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.crl.mv.constant.SysConstant;

public final class COSClient {

	private static AmazonS3 _cos = null;

	public static AmazonS3 getClient() {

		if (_cos == null) {

			String api_key = ConfigUtil.getProperties(SysConstant.API_KEY);
			String service_instance_id = ConfigUtil.getProperties(SysConstant.Service_Instance_Id);
			String endpoint_url = ConfigUtil.getProperties(SysConstant.Endpoint_URL);
			String location = ConfigUtil.getProperties(SysConstant.Location);
			createClient(api_key, service_instance_id, endpoint_url, location);
		}

		
		return _cos;
	}
	
	
	public static void createClient(String api_key, String service_instance_id, String endpoint_url, String location) {

		SDKGlobalConfiguration.IAM_ENDPOINT = "https://iam.bluemix.net/oidc/token";
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
		clientConfig.setSocketTimeout(ClientConfiguration.DEFAULT_SOCKET_TIMEOUT * 3);
		clientConfig.setRequestTimeout(1000 * 60 * 3);

		_cos = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withEndpointConfiguration(new EndpointConfiguration(endpoint_url, location))
				.withPathStyleAccessEnabled(true).withClientConfiguration(clientConfig).build();

	}

	public static void shutdown() {

		if (_cos != null)
			_cos.shutdown();
	}

}
