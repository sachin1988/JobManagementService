package com.jobmanagement.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;


/**
 * @author sachinkatarnaware
 * 
 * This class is used for loading the configuration files when we start the
 * the system.
 *
 */
public class PropsFileUtil {
    
   

   public PropsFileUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

public Properties  properties = new Properties();

   public boolean loadFile(String filePath){
       try {
           FileInputStream f = new FileInputStream(filePath);
           properties.load(f);
           f.close();
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

   
   public boolean addProperties(Properties map,String fileName) {
       try {
           Set<?> set = map.keySet();
           Iterator<?> itr = set.iterator();
           while (itr.hasNext()) {
               String key = (String) itr.next();
               String value = (String)map.get(key);
               properties.setProperty(key, value);
           }
           FileOutputStream fop = new FileOutputStream(fileName);
           properties.store(fop,JobManagementConstants.jPropertiesStoreMessage );
           fop.close();
           return true;

       } catch (Exception e) {
           return false;
       }
   }

}