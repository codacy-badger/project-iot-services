package com.ibm.crl.mv.maximo.model;

import java.util.ArrayList;
import java.util.List;

public class AssetRequest {
	
	private String assetNumber;
	private String des;
	private	String srtype;
	private String longDes;
	private String location;
	private String reportDate;
	private String status;
	private List<FileModel> fileList = new ArrayList<>();

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	public String getSrtype() {
		return srtype;
	}

	public void setSrtype(String srtype) {
		this.srtype = srtype;
	}

	public String getLongDes() {
		return longDes;
	}

	public void setLongDes(String longDes) {
		this.longDes = longDes;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public List<FileModel> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileModel> fileList) {
		this.fileList = fileList;
	}

	
	
	
}
