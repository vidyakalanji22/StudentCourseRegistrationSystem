/**
 * 
 */
package com.scr.util;

import java.io.IOException;
import java.util.Properties;


public abstract class PropertyLoader {

	private static Properties dbProperties = null;
	private static Properties confProperties = null;

	/**
	 * @return the dbProperties
	 */
	public static Properties getDbProperties() {
		if (dbProperties != null) {
			return dbProperties;
		}

		try {
			dbProperties = new Properties();
			//String current = new java.io.File( "." ).getCanonicalPath();
		    //System.out.println("Current dir:"+current);
		    //String currentDir = System.getProperty("user.dir");
		   // System.out.println(PropertyLoader.class.get);
		    dbProperties = new Properties(); 
		    dbProperties.load(PropertyLoader.class.getResourceAsStream("/dbqueries.properties"));
			/*FileInputStream inputStream = new FileInputStream("src/main/resources/dbqueries.properties");
			System.out.println("["+inputStream+"]");
			dbProperties.load(inputStream);
			System.out.println("db queries properties loaded");
			inputStream.close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbProperties;
	}

	/**
	 * @return the confProperties
	 */
	public static Properties getConfProperties() {
		if (confProperties != null) {
			return confProperties;
		}

		try {
			confProperties = new Properties();
			confProperties.load(PropertyLoader.class.getResourceAsStream("/conf.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return confProperties;
	}

}
