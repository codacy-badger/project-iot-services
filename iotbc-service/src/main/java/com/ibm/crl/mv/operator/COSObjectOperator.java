package com.ibm.crl.mv.operator;

import java.io.File;
import java.nio.file.AccessDeniedException;

import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;

public interface COSObjectOperator {
	
	
	//上传对象
	public String uploadObject(AmazonS3 _cos, String bucket, String fileName, File file) throws SdkClientException;
	
	//获取对象
	public String getObject(AmazonS3 _cos, String bucket, String filename) throws SdkClientException;
	
	//上传多个对象
	public String multiPartUpload(AmazonS3 _cos, String bucket, String filePath) throws SdkClientException, AccessDeniedException;
	
	
	

}
