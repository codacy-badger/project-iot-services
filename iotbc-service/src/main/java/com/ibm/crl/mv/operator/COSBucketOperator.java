package com.ibm.crl.mv.operator;


import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;

public interface COSBucketOperator{
	

	
	public void createBucket(AmazonS3 _cos, String bucketName)throws SdkClientException;

	public boolean doesBucketExist(AmazonS3 _cos, String bucket)throws SdkClientException;

	public void delBucket(AmazonS3 _cos, String bucketName)throws SdkClientException;

	//public List<?>  listObject(AmazonS3 _cos, String bucketName)throws SdkClientException;

	//public void listBuckets(AmazonS3 _cos)throws SdkClientException;
	
	

	

	

}
