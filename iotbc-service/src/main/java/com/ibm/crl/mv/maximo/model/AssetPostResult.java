package com.ibm.crl.mv.maximo.model;

public class AssetPostResult {
	
	public static enum SRResult {
		Success, Fail;
	}
	
	private String meg;
	private SRResult sResult;
	private SRHistModel sModel;
	
	public String getMeg() {
		return meg;
	}
	public void setMeg(String meg) {
		this.meg = meg;
	}
	
	public SRResult getsResult() {
		return sResult;
	}
	public void setsResult(SRResult sResult) {
		this.sResult = sResult;
	}
	
	public SRHistModel getsModel() {
		return sModel;
	}
	public void setsModel(SRHistModel sModel) {
		this.sModel = sModel;
	}
	

}
