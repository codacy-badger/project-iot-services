package com.ibm.crl.mv.maximo.model;

import java.util.ArrayList;
import java.util.List;

public class SRModel {

   private String $class;
   private String ticketid;
   private String srtype;
   private String description;
   private String description_longdescription;
   private String location;
   private String reportdate;
   private String actualstart;
   private String status;
   private String status_description;
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

   public void setDescription_longdescription(String description_longdescription) {
        this.description_longdescription = description_longdescription;
    }
    public String getDescription_longdescription() {
        return description_longdescription;
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

   public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }
    public String getStatus_description() {
        return status_description;
    }

    public List<SRDocs> getDocs() {
    	return docs;
    }
    public void setDocs(List<SRDocs> docs) {
    	this.docs = docs;
    }

}