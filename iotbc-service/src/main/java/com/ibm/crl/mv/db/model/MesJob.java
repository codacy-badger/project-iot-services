package com.ibm.crl.mv.db.model;

public class MesJob extends Mes {

	private String record_ID;
	
	private String transaction_ID;
	
	private String create_time;

	public final String getRecord_ID() {
		return record_ID;
	}

	public final void setRecord_ID(String record_ID) {
		this.record_ID = record_ID;
	}

	public final String getTransaction_ID() {
		return transaction_ID;
	}

	public final void setTransaction_ID(String transaction_ID) {
		this.transaction_ID = transaction_ID;
	}

	public final String getCreate_time() {
		return create_time;
	}

	public final void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	
	
}
