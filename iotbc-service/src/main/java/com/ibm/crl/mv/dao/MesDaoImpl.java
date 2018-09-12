package com.ibm.crl.mv.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.ibm.crl.mv.db.model.InsertOperator;
import com.ibm.crl.mv.db.model.MesDoc;
import com.ibm.crl.mv.utils.DBMaker;

public class MesDaoImpl implements MesDao {

	private static Logger log = LoggerFactory.getLogger(MesDaoImpl.class);

	private static MesDaoImpl instance = new MesDaoImpl();

	public static MesDao getInstance() {
		

		return instance;

	}

	@Override
	public boolean insertRecord(java.util.List<InsertOperator> insertData) throws SQLException {

		try (DruidPooledConnection conn = DBMaker.getConn();) {

			if (insertData == null || insertData.isEmpty()) {
				log.info("The Param 'mesDocs' is null");
				return false;
			}
			conn.setAutoCommit(false);
			
			int total = 0;

			for (InsertOperator iOperator : insertData) {


				Map<String, Object> mapper = iOperator.getRecords().get(0);

				String tableName = iOperator.getTableName();

				Set<String> keys = mapper.keySet();

				StringBuilder columnSql = new StringBuilder("INSERT INTO ");

				StringBuilder paramSql = new StringBuilder();

				columnSql.append(tableName).append(" ( ");

				int pos = 0;

				String[] keyPos = new String[keys.size()];

				for (Iterator<String> it = keys.iterator(); it.hasNext();) {

					String key = it.next();
					// append column
					columnSql.append(pos == 0 ? "" : ",").append(key);
					// append param
					paramSql.append(pos == 0 ? "" : ",").append("?");

					keyPos[pos] = key;

					pos++;
				}

				columnSql.append(" ) values (").append(paramSql.toString()).append(" )");

				PreparedStatement ps = conn.prepareStatement(columnSql.toString());

				log.info("Method[insertRecord] >>> Execute SQL  : " + columnSql.toString());

				for (Iterator<Map<String, Object>> it = iOperator.getRecords().iterator(); it.hasNext();) {

					Map<String, Object> values = it.next();

					for (int i = 0; i < keyPos.length; i++) {

						int paramIndex = i + 1;

						ps.setObject(paramIndex, values.get(keyPos[i]));
					}

					ps.addBatch();
				}

				int rows = ps.executeBatch().length;

				total += rows;


			}
			
			conn.commit();
			
			log.info(String.format("Success to insert %s rows", total));
			return true;

		} catch (SQLException e) {
			log.error("Fail to insert data !!!", e);
			throw e;
		}

	}


	@Override
	public MesDoc selectDocByHash(String tableName, String hash) throws SQLException {
		
		
		MesDoc mesDoc = new MesDoc();
		
		try(DruidPooledConnection conn = DBMaker.getConn()){
			
			String sql = "select *  from  " + tableName + " where hash_key = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, hash);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				String hash_key = rs.getString("hash_key");
				mesDoc.setHash_key(hash_key);
				
				String doc_URL = rs.getString("doc_URL");
				mesDoc.setDoc_URL(doc_URL);
				
				String record_ID = rs.getString("record_ID");
				mesDoc.setRecord_ID(record_ID);
				
				String create_time = rs.getString("create_time");
				mesDoc.setCreate_time(create_time);
				
			}
			
		}
		
		return mesDoc;
		
	}

}
