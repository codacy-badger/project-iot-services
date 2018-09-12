package com.ibm.crl.mv.operator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.AbortMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.CompleteMultipartUploadRequest;
//import com.ibm.cloud.objectstorage.services.s3.model.GetObjectAclRequest;
import com.ibm.cloud.objectstorage.services.s3.model.InitiateMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.InitiateMultipartUploadResult;
import com.ibm.cloud.objectstorage.services.s3.model.PartETag;
import com.ibm.cloud.objectstorage.services.s3.model.UploadPartRequest;
import com.ibm.cloud.objectstorage.services.s3.model.UploadPartResult;

public class COSOperatorImpl implements COSBucketOperator, COSObjectOperator {

	private static Logger log = LoggerFactory.getLogger(COSOperatorImpl.class);

	private static COSOperatorImpl operator = null;

	public static COSOperatorImpl getInstance() {

		if (operator == null)
			operator = new COSOperatorImpl();

		return operator;

	}

	@Override
	public String uploadObject(AmazonS3 _cos, String bucketName, String filename, java.io.File file)
			throws SdkClientException {

		_cos.putObject(bucketName, filename, file);
		
		log.info(String.format("Bucket: %s , File : %s, uploaded!", bucketName, filename));

		return _cos.getUrl(bucketName, filename).toString();
	}

	/**
     * <p>
     * Retrieves and decodes the contents of an S3 object to a String.
     * </p>
     *
     * @param bucketName
     *            The name of the bucket containing the object to retrieve.
     * @param filename
     *            The filename of the object to retrieve.
     * @return contents of the object as a String
     */
	@Override
	public String getObject(AmazonS3 _cos, String bucketName, String filename) throws SdkClientException {
		return _cos.getObjectAsString(bucketName, filename);
	}

	
	@Override
	public void createBucket(AmazonS3 _cos, String bucketName) throws SdkClientException {

		_cos.createBucket(bucketName);

		log.info(String.format("Bucket: %s created!", bucketName));

	}

	@Override
	public List<?> listObject(AmazonS3 _cos, String bucketName) throws SdkClientException {

		return null;
	}

	@Override
	public void delBucket(AmazonS3 _cos, String bucketName) throws SdkClientException {

		_cos.deleteBucket(bucketName);

		log.info(String.format("Bucket: %s deleted!", bucketName));
	}

	@Override
	public void listBuckets(AmazonS3 _cos) throws SdkClientException {
		return;
	}

	@Override
	public boolean doesBucketExist(AmazonS3 _cos, String bucket) throws SdkClientException {

		return _cos.doesBucketExist(bucket);
	}

	//获取到的是URl
	@Override
	public String multiPartUpload(AmazonS3 _cos, String bucket, String filePath) throws SdkClientException {


		File file = new File(filePath);

		String name = file.getName();

		log.info(String.format("Starting multi-part upload for %s to bucket: %s\n", name, bucket));

		InitiateMultipartUploadResult mpResult = _cos
				.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucket, name));
		String uploadID = mpResult.getUploadId();
		

		// begin uploading the parts
		// min 2MB part size
		long partSize = 1024 * 1024 * 2;
		long fileSize = file.length();
		long partCount = ((long) Math.ceil(fileSize / partSize)) + 1;
		List<PartETag> dataPacks = new ArrayList<PartETag>();

		try {
			long position = 0;
			for (int partNum = 1; position < fileSize; partNum++) {
				partSize = Math.min(partSize, (fileSize - position));

				log.info(String.format("Uploading to %s (part %s of %s)\n", name, partNum, partCount));

				UploadPartRequest upRequest = new UploadPartRequest().withBucketName(bucket).withKey(name)
						.withUploadId(uploadID).withPartNumber(partNum).withFileOffset(position).withFile(file)
						.withPartSize(partSize);

				UploadPartResult upResult = _cos.uploadPart(upRequest);
				dataPacks.add(upResult.getPartETag());

				position += partSize;
			}

			// complete upload
			_cos.completeMultipartUpload(new CompleteMultipartUploadRequest(bucket, name, uploadID, dataPacks));
			
			log.info(String.format("File : %s , uploaded",name));
			
			return _cos.getUrl(bucket, name).toString();

		} catch (SdkClientException sdke) {
			_cos.abortMultipartUpload(new AbortMultipartUploadRequest(bucket, name, uploadID));
			throw sdke;
		}
		
		
	}

}
