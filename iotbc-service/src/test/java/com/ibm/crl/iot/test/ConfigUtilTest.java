package com.ibm.crl.iot.test;

import com.ibm.crl.mv.utils.ConfigUtil;

public class ConfigUtilTest {
	
	public static void main(String[] args) {
		
		
		java.util.Properties prop = ConfigUtil.getProp();
		
		prop.list(System.out);
	}

}
