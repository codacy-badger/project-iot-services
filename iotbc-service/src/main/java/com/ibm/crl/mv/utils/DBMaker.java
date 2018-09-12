package com.ibm.crl.mv.utils;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DBMaker {

	private static Logger log = LoggerFactory.getLogger(DBMaker.class);

	private static DruidDataSource druidDataSource = null;

	public static void createDataSource() {

		try {

			druidDataSource = new DruidDataSource();

			druidDataSource.configFromPropety(ConfigUtil.getProp());

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Msg : " + e.getMessage());

		}

	}

	public static DruidPooledConnection getConn() {

		if (druidDataSource == null) {
			createDataSource();
		}

		try {
			DruidPooledConnection connection = druidDataSource.getConnection();

			return connection;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
