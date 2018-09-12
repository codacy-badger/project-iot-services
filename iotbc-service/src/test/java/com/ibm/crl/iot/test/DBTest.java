package com.ibm.crl.iot.test;

import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

public class DBTest {

	public static void main(String[] args) throws SQLException {

		Properties prop = new Properties();

		prop.put("druid.driverClassName", "com.mysql.cj.jdbc.Driver");
		prop.put("druid.url",
				"jdbc:mysql://9.186.104.45/blockchain?useUnicode=true&characterEncoding=utf8&autoReconnect=true");
		prop.put("druid.username", "root");
		prop.put("druid.password", "passw0rd");
		prop.put("druid.maxActive", "20");

		DruidDataSource druidDataSource = new DruidDataSource();
		
		druidDataSource.configFromPropety(prop);
		
		
		druidDataSource.getConnection();
	}
}
