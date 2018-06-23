package com.br.code.skip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDB {
	
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/skipdb";
		String user = System.getenv("RDS_DB_USER");
        String password = System.getenv("RDS_DB_PASS");
        Connection conn = null;
 
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return conn;
	}
}
