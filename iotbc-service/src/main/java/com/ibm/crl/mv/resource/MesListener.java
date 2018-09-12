package com.ibm.crl.mv.resource;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ibm.crl.mv.constant.SysConstant;
import com.ibm.crl.mv.utils.COSClient;
import com.ibm.crl.mv.utils.ConfigUtil;
import com.ibm.crl.mv.utils.DBMaker;


public class MesListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}


	
	private static String path = "config.properties";

	@Override
	public void contextInitialized(ServletContextEvent sce) {


		java.io.InputStream input  = sce.getServletContext().getClassLoader().getResourceAsStream(path);
		
		ConfigUtil.load(input);
		
		DBMaker.createDataSource();
		
//		sce.getServletContext().getRealPath("/WEB-INF/upload");
		
		String api_key = ConfigUtil.getProperties(SysConstant.API_KEY);
		String service_instance_id = ConfigUtil.getProperties(SysConstant.Service_Instance_Id);
		String endpoint_url = ConfigUtil.getProperties(SysConstant.Endpoint_URL);
		String location = ConfigUtil.getProperties(SysConstant.Location);
		
		COSClient.createClient(api_key, service_instance_id, endpoint_url, location);
		
		
		
	}

	

}
