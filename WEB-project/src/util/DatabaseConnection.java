package util;
import java.sql.*;

public class DatabaseConnection {
	static String serverIp= "155.230.36.61";
	static String strSID="orcl";
	static String portNum="1521";
	static String user="s2017110115";
	static String pass="qaz123wsx456";
	static String url="jdbc:oracle:thin:@" + serverIp + ":" + portNum + ":" + strSID;
	
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
