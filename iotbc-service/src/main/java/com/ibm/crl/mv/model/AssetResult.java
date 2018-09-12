package com.ibm.crl.mv.model;

import java.util.ArrayList;
import java.util.List;

public class AssetResult {
	
	private String $class;
	private String ticketid;
	private String longDescription;
	private String status;
	private List<AssetDocs> docs = new ArrayList<>();
	
	public String get$class() {
		return $class;
	}
	public void set$class(String $class) {
		this.$class = $class;
	}
	public String getTicketid() {
		return ticketid;
	}
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<AssetDocs> getDocs() {
		return docs;
	}
	public void setDocs(List<AssetDocs> docs) {
		this.docs = docs;
	}
	

}
