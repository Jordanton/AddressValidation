package com.itafin.addressvalidation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {
	
	public static String LoadProp(String propName) {
		Properties prop = new Properties();
    	InputStream input = null;
    	
    	try {
        
    		String filename = "melissadata.properties";
    		input = PropsUtil.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		}

    		//load a properties file from class path, inside static method
    		prop.load(input);
    	        	
	        return prop.getProperty(propName);
    	        
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
		return null;
	}
	
}
