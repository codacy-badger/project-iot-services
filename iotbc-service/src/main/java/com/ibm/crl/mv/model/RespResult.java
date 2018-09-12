package com.ibm.crl.mv.model;

import java.util.ArrayList;
import java.util.List;

import com.ibm.crl.mv.utils.DateUtils;

public class RespResult {
	
	public static enum  ResStatus{
		Success, Fail;
	}
	
	

	private String msg;
	
	private ResStatus status = ResStatus.Success;
	
	private String recordId;
	
	private List<Object> fineResult = new ArrayList<>();
	
	private List<Object> errResult = new ArrayList<>();
	
	private String time = DateUtils.getStringDate();
	

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResStatus getStatus() {
		return status;
	}

	public void setStatus(ResStatus status) {
		this.status = status;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Object> getFineResult() {
		return fineResult;
	}

	public List<Object> getErrResult() {
		return errResult;
	}

	public void setErrResult(List<Object> errResult) {
		this.errResult = errResult;
	}

	public void setFineResult(List<Object> fineResult) {
		this.fineResult = fineResult;
	}

	
	
	
}
