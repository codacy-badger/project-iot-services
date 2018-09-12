package com.ibm.crl.mv.model;

public abstract class Descriptor {

	
//	public String $class;
	
	
	public String ticketid;
	
	public String longDescription;
	
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
	
	
	
}
