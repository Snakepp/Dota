package com.dota.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.vaadin.server.VaadinService;

public class PropertyManager {
	
	private Properties properties;
	private InputStream input = null;
	
	private final static String DB_PROP = "database";
	private final static String DB_USER_PROP = "database_user";
	private final static String DB_PASS_PROP = "database_pass";
	private final static String DB_PORT_PROP = "database_port";
	private final static String DB_HOST_PROP = "database_host";
	private final static String URL_PROP = "url";
	private final static String CURRENT_PATH_PROP = "currentPath";
	private final static String ENVIRONMENT_TEST = "TEST";
	private final static String ENVIRONMENT_PROD = "PROD";
	
	
	/**
	 * Change this dependent on the environment you will run the application
	 */
	private final static String ENVIRONMENT = ENVIRONMENT_PROD; 
	
	
	
	public PropertyManager(){
		properties = new Properties();
		try {
			input = new FileInputStream(getPropertyFilePath());
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private String getPropertyFilePath(){
//		TODO: change this to get the property file

		if(ENVIRONMENT.equals(ENVIRONMENT_TEST)){
//			Test environment
			String dir = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
			return dir+"/WEB-INF/config.props";
		}else{
//			Production Environment
			String dir = getProperty("OPENSHIFT_DATA_DIR", "")+"config.props";
			return dir;
		}
	}
	
	private String getProperty(String environmentVar, String property){
		String var = System.getenv(environmentVar);
		if(var==null){
			var = properties.getProperty(property);
		}
		return var;
	}
	public String getURL(){
		return properties.getProperty(URL_PROP);
	}
	public String getDB(){
		return properties.getProperty(DB_PROP);
	}
	public String getDBHost(){
		return getProperty("OPENSHIFT_MYSQL_DB_HOST", DB_HOST_PROP);
	}
	public String getDBUser(){
		return getProperty("OPENSHIFT_MYSQL_DB_USERNAME", DB_USER_PROP);
	}
	public String getDBPass(){
		return getProperty("OPENSHIFT_MYSQL_DB_PASSWORD", DB_PASS_PROP);
	}
	public String getDBPort(){
		return getProperty("OPENSHIFT_MYSQL_DB_PORT", DB_PORT_PROP);
	}
	public String getCurrentpath(){
		String dir = getProperty("OPENSHIFT_DATA_DIR", CURRENT_PATH_PROP);
		if(dir==null){
			dir = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		}
		return dir; 
	}
	
	public String getHeroesPath(){
		String heroesPath = null;
		if(ENVIRONMENT.equals(ENVIRONMENT_TEST)){
			heroesPath = getCurrentpath()+"/VAADIN/themes/my-chameleon/images/Heroes/";
		}else{
			heroesPath = getCurrentpath()+"/images/images/Heroes/";
		}
		return heroesPath;
	}
	public String getLogoPath(){
		String heroesPath = null;
		if(ENVIRONMENT.equals(ENVIRONMENT_TEST)){
			heroesPath = getCurrentpath()+"/VAADIN/themes/my-chameleon/images/logo/";
		}else{
			heroesPath = getCurrentpath()+"/images/images/logo/";
		}
		return heroesPath;
	}
//	TODO:
//	Local environment
//	connection = new DBConnection("localhost", "root", "", "ghost", "3306");
//		Local environment(Port forwarding)
//	connection = new DBConnection("127.0.0.1", "adminQT3aEtS", "AMF84AreG5ZU", "ghost", "3307");
	

}
