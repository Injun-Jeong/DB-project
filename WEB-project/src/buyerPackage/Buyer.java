package buyerPackage;

import domain.order.*;
import domain.vehicle.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import accountPackage.Account;

public class Buyer {
	/* attribute */
	private static Connection conn = null;
	private static ArrayList<Order> orderList = null;
	private static ArrayList<Vehicle> vehicleList = null;

	public Buyer() {
		
	}

	/* method */
	public static ArrayList<Order> getOrder(int userId) {
		
		if (conn == null)
			conn = DatabaseConnection.getConnection();
		
		if (orderList == null) {
			orderList = new ArrayList<Order>();
		} else {
			orderList.clear();
		}

		String sql = "SELECT * FROM \"ORDER\" WHERE Buyer = ? ORDER BY Order_num DESC";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				orderList.add(new Order(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderList;
	}

	public static ArrayList<Vehicle> searchAdvanced(String make, String model, String detail, String transmission,
			String condition, ArrayList<String> colors, ArrayList<String> fuels, String displacement) {
		
		if (conn == null)
			conn = DatabaseConnection.getConnection();
		
		if (vehicleList == null) {
			vehicleList = new ArrayList<Vehicle>();
		} else {
			vehicleList.clear();
		}

		ArrayList<String> option = new ArrayList<>();
		int count = 0;
		boolean terminator = false;

		String sql = "SELECT Reg_num, Transmission, ReleaseDate, Vin, Condition, is_sold, Displacement, Price, Detailed_model, Registerer, Category, Mpg, Mileage";
		String from = "from (((make mk join \"MODEL\" m on mk.make_num = m.manufacturer) "
				+ "join detailed_model d on m.model_num = d.parent_model) "
				+ "join vehicle v on d.detail_num = v.detailed_model)";
		String where = "where v.is_sold = \'FALSE\' and v.is_viewable = \'TRUE\' ";
		String column = null;

		String str = "Make:" + make + "+Model:" + model + "+Detail:" + detail + "+Transmission:" + transmission
				+ "+Condition:" + condition + "+Color:";
		
		if (colors.size() > 1)
			str = str + colors.get(0) + "/" + colors.get(1) + "+Fuel:";
		else
			str = str + colors.get(0)  + "+Fuel:";
		
		if (fuels.size() > 1)
			str = str + fuels.get(0) + "/" + fuels.get(1) + "+Displacement:" + displacement;
		else
			str = str + fuels.get(0) + "+Displacement:" + displacement;
		

		
		if (!terminator) {
			String detailData = "#";
			String flag = "";
			StringTokenizer option_token = new StringTokenizer(str, "+");
			while (option_token.hasMoreTokens()) {
				option.add(option_token.nextToken());
				StringTokenizer detail_token = new StringTokenizer(option.get(count++), ":/");
				
				flag = detail_token.nextToken();
				detailData = detail_token.nextToken();
				
				if (detailData.equals("#"))
					continue;
				
				switch (flag) {
				case "Make":
					column = "mk.make_name";
					break;
				case "Model":
					column = "m.model_name";
					break;
				case "Detail":
					column = "d.detail_name";
					break;
				case "Transmission":
					from += ", transmission t";
					where += "and v.transmission = t.transmission_num ";
					column = "t.transmission_type";
					break;
				case "Condition":
					from += ", condition cond";
					where += "and v.condition = cond.condition_num ";
					column = "cond.grade";
					break;
				case "Category":
					from += ", category ca";
					where += "and v.category = ca.category_num ";
					column = "ca.category_name";
					break;
				case "Displacement":
					from += ", engine_displacement ed";
					where += "and v.displacement = ed.ed_num ";
					column = "ed.ed_value";
					break;
				case "Color":
					from += ", color col, paints p";
					where += "and col.color_num = p.color_num " + "and v.reg_num = p.reg_num ";
					column = "col.color_name";
					break;
				case "Fuel":
					from += ", fuel f, uses u";
					where += "and f.fuel_num = u.fuel_num " + "and v.reg_num = u.reg_num ";
					column = "f.fuel_type";
					break;
				default:
					// wrong input!
					terminator = true;
					break;
				}
				if (terminator) {
					System.out.println("error: wrong format");
					break;
				}
				
				where += "and " + column + " in (\'" + detailData + "\'";
				while (detail_token.hasMoreTokens())
					where += ", " + "\'" + detail_token.nextToken() + "\'";
				where += ") ";
			}
			if (!terminator) {
				sql += " " + from + " " + where + "order by v.reg_num asc ";
				try {
					// search all registered vehicles with advanced options
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						vehicleList.add(new Vehicle(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6),
							rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getString(12),
							rs.getInt(13), rs.getInt(14)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return vehicleList;
	}

	
	public static boolean purchase(int regNum, int login_account) {
		
		if (conn == null)
			conn = DatabaseConnection.getConnection();
		
		boolean res = false;
		Savepoint savepoint=null;
		try {
			conn.setAutoCommit(false);
			DatabaseMetaData dbmd = conn.getMetaData();
			if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			savepoint=conn.setSavepoint();
			CallableStatement cs = conn.prepareCall("{call buyingProcess(?,?)}");
			cs.setInt(1, regNum);
			cs.setInt(2, login_account);
			res = cs.execute();
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback(savepoint);
				conn.setAutoCommit(true);
				return false;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	/* getter and setter */

}
