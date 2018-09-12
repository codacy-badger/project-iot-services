package com.ibm.crl.mv.model;

import java.util.ArrayList;
import java.util.List;

public class VSResult {
	
	private String $class;
	
	private String ticketid;
	
	private String long_description;
	
	private String status;
	
	private List<VSDoc> vDocs = new ArrayList<>();

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

	public String getLong_description() {
		return long_description;
	}

	public void setLong_description(String long_description) {
		this.long_description = long_description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<VSDoc> getvDocs() {
		return vDocs;
	}

	public void setvDocs(List<VSDoc> vDocs) {
		this.vDocs = vDocs;
	}
	

}
