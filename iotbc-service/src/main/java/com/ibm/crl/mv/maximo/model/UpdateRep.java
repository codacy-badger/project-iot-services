package com.ibm.crl.mv.maximo.model;


import java.util.Date;


public class UpdateRep {

   private String $class;
   private SRModel inspection;
   private String transactionId;
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

   public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getTransactionId() {
        return transactionId;
    }

   public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Date getTimestamp() {
        return timestamp;
    }

}