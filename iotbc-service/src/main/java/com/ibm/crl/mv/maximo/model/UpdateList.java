package com.ibm.crl.mv.maximo.model;

import java.util.ArrayList;
import java.util.List;

public class UpdateList {
	
	public static enum HistStatus {
		Success, Fail;
	}
	
	private HistStatus hStatus;
	private Boolean download;
	private String msg;
	private List<SRHistModel> uList = new ArrayList<>();
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getDownload() {
		return download;
	}
	public void setDownload(Boolean download) {
		this.download = download;
	}
	
	public HistStatus gethStatus() {
		return hStatus;
	}
	public void sethStatus(HistStatus hStatus) {
		this.hStatus = hStatus;
	}
	
	public List<SRHistModel> getuList() {
		return uList;
	}
	public void setuList(List<SRHistModel> uList) {
		this.uList = uList;
	}
	
}
