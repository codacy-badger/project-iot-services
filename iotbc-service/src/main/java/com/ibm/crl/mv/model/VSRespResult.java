package com.ibm.crl.mv.model;

public class VSRespResult {
	

	public static enum  VSResStatus{
		Success, Fail;
	}
	
	private String msg;
	private VSResStatus resStatus;
	private VSResult vsResult;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public VSResStatus getResStatus() {
		return resStatus;
	}
	public void setResStatus(VSResStatus resStatus) {
		this.resStatus = resStatus;
	}
	public VSResult getVsResult() {
		return vsResult;
	}
	public void setVsResult(VSResult vsResult) {
		this.vsResult = vsResult;
	}
	

}
