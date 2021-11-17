package com.myWeb.model.configuration;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.persistence.PersistenceManager;

import java.io.*;
import java.util.Properties;

public class DataHandler {
	private static PersistenceManager prManager = null;

	public static String fileHandler() throws CustomizedException {
		String className;
	//	String userDirectory = System.getProperty("user.home");
		try {
			InputStream reader = new FileInputStream("C:\\Users\\inc5\\backup\\eclipse-workspace\\jspservlet\\Sample\\src\\main\\java\\mySQLDB.properties");
			Properties propertyFile = new Properties();
			propertyFile.load(reader);
			String dbName = "MySQL";

			className = propertyFile.getProperty(dbName);
			if (className.isEmpty()) {
				reader.close();
				System.out.println("Class Name Not Found");
				System.exit(0);
			}
			return className;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new CustomizedException("Property File Read Failed");
		}
	}

	private static void loadClassPersistence() throws CustomizedException {
		String className = "com.myWeb.model.dbLayer.DBOperation";
		try {
			Class<?> classDb = Class.forName(className);
			prManager = (PersistenceManager) classDb.newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new CustomizedException("Class not Found");
		}
	}

	public static PersistenceManager getPersistenceManager() throws CustomizedException {
		if (prManager == null) {
			loadClassPersistence();
		}
		return prManager;
	}
}
