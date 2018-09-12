package com.ibm.crl.mv.maximo.model;


public class SRHistModel {

   private String $class;
   private String ticketid_time;
   private UpdateRep evt;
   
   public void set$class(String $class) {
        this.$class = $class;
    }
    public String get$class() {
        return $class;
    }

   public void setTicketid_time(String ticketid_time) {
        this.ticketid_time = ticketid_time;
    }
    public String getTicketid_time() {
        return ticketid_time;
    }

   public void setEvt(UpdateRep evt) {
        this.evt = evt;
    }
    public UpdateRep getEvt() {
        return evt;
    }

}