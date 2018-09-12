package com.ibm.crl.mv.db.model;

public class MesDoc extends Mes{

	private String hashKey;
	
	private String docURL;
	
	private String recordID;
	
	private String createTime;


	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getDocURL() {
		return docURL;
	}

	public void setDocURL(String docURL) {
		this.docURL = docURL;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
