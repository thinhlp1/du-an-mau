/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private String dbName = "EduSys";
        private String user = "sa";
        private String pass = "sa";
	
	public Connection getConnection() {
		String url = "jdbc:sqlserver://" + serverName + ":" + ipPort + "\\" + instance + ";database="+dbName;
		if (instance == null || instance.trim().isEmpty()) {
			url =  "jdbc:sqlserver://" + serverName + ":" + ipPort + ";database="+dbName;
		}
		try {
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	private String serverName = "THINHHH\\SQLEXPRESS";
	private String ipPort = "1433";
	private String instance = "";

}