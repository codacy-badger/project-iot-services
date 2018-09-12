package com.ibm.crl.mv.maximo.model;


import java.util.Date;

public class UpdateReq {

   private String $class;
   private SRModel inspection;
   private Date timestamp;
   
   public void set$class(String $class) {
        this.$class = $class;
    }
    public String get$class() {
        return $class;
    }

   public void setInspection(SRModel inspection) {
        this.inspection = inspection;
    }
    public SRModel getInspection() {
        return inspection;
    }

   public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Date getTimestamp() {
        return timestamp;
    }

}