package com.ibm.crl.mv.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {

	private static Logger log = LoggerFactory.getLogger(ConfigUtil.class);

	private static Properties prop = new Properties();

//	private static String path = "./config.properties";

	// static {
	//
	// try {
	//
	// InputStream input = ClassLoader.getSystemResourceAsStream(path);
	//
	// prop.load(input);
	//
	// log.info("Load configuration file.... ");
	//
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	//
	// }
	

	public static String Mes_Persist_Dir = "mes.persist.dir";


	public static void load(InputStream input) {

		try {

			prop.load(input);
			log.info("Load configuration file.... ");

		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("Fail to close load config stream", e);
				}
			}
		}

	}
	
	

	public static String getProperties(String key) {

		return prop.getProperty(key);
	}
	

	public static Properties getProp() {

		return prop;
	}

	public static String getPersistDir() {

		String dir = prop.getProperty(Mes_Persist_Dir);

		File file = new File(dir);

		if (!file.exists() || !file.isDirectory())
			file.mkdirs();

		return dir;
	}

	public static int getAPIVersion() {
		return Integer.parseInt(prop.getProperty("api.version"));
	}
}
