package com.ibm.crl.mv.model;

public class RespResultInfo {

	private String filename;
	
	private String hash_key;
	
	private String tiketid;
	
	private String msg;
	
	
	


	public RespResultInfo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHash_key() {
		return hash_key;
	}

	public void setHash_key(String hash_key) {
		this.hash_key = hash_key;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTiketid() {
		return tiketid;
	}

	public void setTiketid(String tiketid) {
		this.tiketid = tiketid;
	}
	
	
	
}
