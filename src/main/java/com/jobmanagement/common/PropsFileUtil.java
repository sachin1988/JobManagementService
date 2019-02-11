package com.jobmanagement.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sachinkatarnaware
 * 
 *         This class is used for loading the configuration files when we start
 *         the the system.
 *
 */
public class PropsFileUtil {

	final static Log logger = LogFactory.getLog(PropsFileUtil.class);

	public PropsFileUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Properties properties = new Properties();

	public boolean loadFile(String filePath) {
		try (FileInputStream f = new FileInputStream(filePath)) {
			properties.load(f);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getProperty(String key) {
		try {
			String value = (String) properties.getProperty(key);
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addProperties(Properties map, String fileName) {
		
		try (FileOutputStream fop = new FileOutputStream(fileName)){
			Set<?> set = map.keySet();
			Iterator<?> itr = set.iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				String value = (String) map.get(key);
				properties.setProperty(key, value);
			}
			
			properties.store(fop, JobManagementConstants.jPropertiesStoreMessage);
			return true;
		} catch (Exception e) {
			return false;
		} 
	}

}