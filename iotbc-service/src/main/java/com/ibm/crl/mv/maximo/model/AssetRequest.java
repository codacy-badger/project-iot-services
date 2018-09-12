package com.ibm.crl.mv.maximo.model;

import java.util.ArrayList;
import java.util.List;

public class AssetRequest {
	
	private String asset_number;
	private String des;
	private	String srtype;
	private String long_des;
	private String location;
	private String report_date;
	private String status;
	private List<FileModel> file_list = new ArrayList<>();
	

	public String getAsset_number() {
		return asset_number;
	}

	public void setAsset_number(String asset_number) {
		this.asset_number = asset_number;
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

	public String getLong_des() {
		return long_des;
	}

	public void setLong_des(String long_des) {
		this.long_des = long_des;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReport_date() {
		return report_date;
	}

	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FileModel> getFile_list() {
		return file_list;
	}

	public void setFile_list(List<FileModel> file_list) {
		this.file_list = file_list;
	} 
	
	
}
