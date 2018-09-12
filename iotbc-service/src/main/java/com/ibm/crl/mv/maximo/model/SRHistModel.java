package com.ibm.crl.mv.maximo.model;


public class SRHistModel {

   private String $class;
   private String ticketidTime;
   private UpdateRep evt;
   
   public void set$class(String $class) {
        this.$class = $class;
    }
    public String get$class() {
        return $class;
    }

   public String getTicketidTime() {
		return ticketidTime;
	}
	public void setTicketidTime(String ticketidTime) {
		this.ticketidTime = ticketidTime;
	}
public void setEvt(UpdateRep evt) {
        this.evt = evt;
    }
    public UpdateRep getEvt() {
        return evt;
    }

}