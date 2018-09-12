package com.ibm.crl.mv.model;

public abstract class Descriptor {

	
//	public String $class;
	
	
	public String ticketid;
	
	public String long_description;
	
	public String status;

	

//	public String get$class() {
//		return $class;
//	}
//
//	public void set$class(String $class) {
//		this.$class = $class;
//	}

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
	
	
	
}
