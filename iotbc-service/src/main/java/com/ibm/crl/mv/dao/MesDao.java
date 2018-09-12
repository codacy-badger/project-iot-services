package com.ibm.crl.mv.dao;



import java.sql.SQLException;
import java.util.List;

import com.ibm.crl.mv.db.model.InsertOperator;
import com.ibm.crl.mv.db.model.MesDoc;

public interface MesDao {
	
	
	public boolean insertRecord(List<InsertOperator> insertData)throws SQLException ;
	
	
	public MesDoc selectDocByHash(String tableName, String hash) throws SQLException;
	

}
