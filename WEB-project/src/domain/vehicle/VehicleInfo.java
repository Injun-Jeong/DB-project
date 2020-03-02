package domain.vehicle;

import domain.vehicle.Vehicle;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class VehicleInfo {
	private static Connection conn = null;
	
	public static Vehicle getVehicleInfo(int regNum) {
		String sql = "SELECT * FROM VEHICLE WHERE Reg_num = " + Integer.toString(regNum);
		Vehicle vehicle = null;
		
		if (conn == null)
			conn = DatabaseConnection.getConnection();
			
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				vehicle = new Vehicle(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getInt(13), rs.getInt(14));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vehicle;
	}
}
