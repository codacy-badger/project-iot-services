package com.ibm.crl.mv.db.model;

import java.util.List;
import java.util.Map;

public class InsertOperator {
	
	private String tableName;
	
	private  List<Map<String, Object>> records;
	

	public final String getTableName() {
		return tableName;
	}

	public final void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public final List<Map<String, Object>> getRecords() {
		return records;
	}

	public final void setRecords(List<Map<String, Object>> records) {
		this.records = records;
	}
	
	

}
