package com.ibm.crl.mv.maximo.model;

import java.util.ArrayList;
import java.util.List;

public class SRModel {

   private String $class;
   private String ticketid;
   private String srtype;
   private String description;
   private String descriptionLongdescription;
   private String location;
   private String reportdate;
   private String actualstart;
   private String status;
   private String statusDescription;
   private List<SRDocs> docs = new ArrayList<>();
   
   public void set$class(String $class) {
        this.$class = $class;
    }
    public String get$class() {
        return $class;
    }

   public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }
    public String getTicketid() {
        return ticketid;
    }

   public void setSrtype(String srtype) {
        this.srtype = srtype;
    }
    public String getSrtype() {
        return srtype;
    }

   public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public String getDescriptionLongdescription() {
        return descriptionLongdescription;
    }

    public void setDescriptionLongdescription(String descriptionLongdescription) {
        this.descriptionLongdescription = descriptionLongdescription;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }

   public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }
    public String getReportdate() {
        return reportdate;
    }

   public void setActualstart(String actualstart) {
        this.actualstart = actualstart;
    }
    public String getActualstart() {
        return actualstart;
    }

   public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public List<SRDocs> getDocs() {
    	return docs;
    }
    public void setDocs(List<SRDocs> docs) {
    	this.docs = docs;
    }

}