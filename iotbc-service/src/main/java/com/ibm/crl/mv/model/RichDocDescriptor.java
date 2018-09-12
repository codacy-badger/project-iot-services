package com.ibm.crl.mv.model;


public class RichDocDescriptor {

	private String fullName;
	
	private byte[] binaryContent;
	
	private String description;
	
	private String hashKey;
	
	private String persistPath;
	
	private String cosUrl;
	
	private String record_ID;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public byte[] getBinaryContent() {
		return binaryContent;
	}

	public void setBinaryContent(byte[] binaryContent) {
		this.binaryContent = binaryContent;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getPersistPath() {
		return persistPath;
	}

	public void setPersistPath(String persistPath) {
		this.persistPath = persistPath;
	}


	public String getCosUrl() {
		return cosUrl;
	}

	public void setCosUrl(String cosUrl) {
		this.cosUrl = cosUrl;
	}

	public String getRecord_ID() {
		return record_ID;
	}

	public void setRecord_ID(String record_ID) {
		this.record_ID = record_ID;
	}

	
	


	
	
}
