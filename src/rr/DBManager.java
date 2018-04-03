package rr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//ror
public class DBManager {
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://aa9tw3of7h33k.cvdomkjagitv.us-east-2.rds.amazonaws.com:3306/ror?autoReconnect=true&useSSL=false", "prdingilian", "P109611639d");
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
