package sellerPackage;

import domain.order.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import util.DatabaseConnection;

public class Seller {
	/* attribute */
	private static Connection conn = null;
	private static ArrayList<Order> orderList = null;
	
	public static  TreeMap<String,Integer> getYearlyStats() {
		
		 TreeMap<String, Integer> stat = new TreeMap<>();
		try {
			if (conn == null)
				conn = DatabaseConnection.getConnection();
			
			String sql = "SELECT TO_CHAR(Order_date,'yyyy'), SUM(Price)\n" + "FROM \"ORDER\" natural join Vehicle\n"
					+ "GROUP BY TO_CHAR(Order_date,'yyyy')\n" + "ORDER BY TO_CHAR(Order_date,'yyyy')";
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				stat.put(res.getString(1),res.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stat;
	}
	public static  TreeMap<String,Integer> getMonthlyStats(String year) {
		
		 TreeMap<String, Integer> stat = new TreeMap<>();
		try {
			if (conn == null)
				conn = DatabaseConnection.getConnection();
			
			String sql = "SELECT TO_CHAR(Order_date,'mm'), SUM(Price)\n" + "FROM \"ORDER\" natural join Vehicle\n"
					+ "WHERE TO_CHAR(Order_date,'yyyy')="+year + "GROUP BY TO_CHAR(Order_date,'mm')\n" + "ORDER BY TO_CHAR(Order_date,'mm')";
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				stat.put(res.getString(1),res.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=1;i<=12;i++) {
			
			if(i<10)
				if(!stat.containsKey("0"+String.valueOf(i))) 
					stat.put("0"+String.valueOf(i), 0);
			else {
				if(!stat.containsKey(String.valueOf(i)))
				stat.put(String.valueOf(i), 0);
			}
			
		}
		return stat;
	}
	public static  TreeMap<String,Integer> getMakeStats() {
		
		 TreeMap<String, Integer> stat = new TreeMap<>();
		try {
			if (conn == null)
				conn = DatabaseConnection.getConnection();
			String sql = "SELECT make_name,sum(price)\n" + "FROM \"ORDER\" NATURAL JOIN VEHICLE,DETAILED_MODEL, MODEL, MAKE\n"
								+ "where detailed_model=detail_num\r\n" + "and parent_model=model_num\n"
								+ "and manufacturer=make_num\n" + "group by make_name";

			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				stat.put(res.getString(1),res.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stat;
	}

	/* method */
	public static boolean createNewCar(int transmission, String releaseDate, int vin, int condition, int displacement,
			int price, int detailedModel, int registerer, int category, int mpg, int mileage, ArrayList<Integer> fuels,
			ArrayList<Integer> colors) {
		
		int regNum = -1;
		int result = -1;
		
		if (conn == null)
			conn = DatabaseConnection.getConnection();

		try {
			Statement st = conn.createStatement();
			PreparedStatement ps;
			String sql = "SELECT MAX(Reg_num) FROM VEHICLE";
			ResultSet resultset = st.executeQuery(sql);
			
			while (resultset.next())
				regNum = resultset.getInt(1) + 1;

			sql = "INSERT INTO VEHICLE (Reg_num,Transmission,Release_date,Vin,Condition,Displacement,Price,Detailed_model,Registerer,Category,MPG,Mileage) "
					+ "VALUES (" + Integer.toString(regNum) + "," + Integer.toString(transmission) + ",TO_DATE('" // transmission
					+ releaseDate + "','mm/yyyy'),'" // release date
					+ Integer.toString(vin) + "'," // vin
					+ Integer.toString(condition) + "," // condition
					+ Integer.toString(displacement) + "," // displacement
					+ Integer.toString(price) + "," // price
					+ Integer.toString(detailedModel) + "," // detailed_model
					+ Integer.toString(registerer) + "," // register
					+ Integer.toString(category) + "," // category
					+ Integer.toString(mpg) + "," // mpg
					+ Integer.toString(mileage) + ")"; // mileage
			
			st.executeUpdate(sql);

			/* the data of fuel */
			for (int fuel : fuels) {
				sql = "INSERT INTO USES (Fuel_num, Reg_num) VALUES (?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setInt(1, fuel);
					ps.setInt(2, regNum);
					ps.executeUpdate();
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}

			/* Checking whether this car is hybrid or not */
			if (fuels.size() > 1) {
				sql = "UPDATE VEHICLE SET Is_hybrid = 'TRUE' WHERE Reg_num = " + regNum;
				try {
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			/* the data of color */
			for (int color : colors) {
				sql = "INSERT INTO PAINTS (Reg_num, Color_num) VALUES (?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setInt(1, regNum);
					ps.setInt(2, color);
					ps.executeUpdate();
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}

			conn.commit();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean updateCarInfo(int regNum, int transmission, String releaseDate, int condition, int displacement,
			int price, int detailedModel, int registerer, int category, int mpg, int mileage, ArrayList<Integer> fuels,
			ArrayList<Integer> colors) {
		int result = -1;

		if (conn == null)
			conn = DatabaseConnection.getConnection();

		try {
			Statement st = conn.createStatement();
			PreparedStatement ps;
			String sql = "UPDATE VEHICLE SET " + "Transmission = " + Integer.toString(transmission)
					+ ", Release_date = TO_DATE('" + releaseDate + "','mm/yyyy')" + ", Condition = "
					+ Integer.toString(condition) + ", Displacement = " + Integer.toString(displacement) + ", Price = "
					+ Integer.toString(price) + ", Detailed_model = " + Integer.toString(detailedModel)
					+ ", Registerer = " + Integer.toString(registerer) + ", Category = " + Integer.toString(category)
					+ ", MPG = " + Integer.toString(mpg) + ", Mileage = " + Integer.toString(mileage)
					+ " WHERE Reg_num = " + Integer.toString(regNum);
			st.executeUpdate(sql);

			/* the data of fuel */
			deleteFuelData(regNum, st);
			for (int fuel : fuels) {
				sql = "INSERT INTO USES (Fuel_num, Reg_num) VALUES (?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setInt(1, fuel);
					ps.setInt(2, regNum);
					ps.executeUpdate();
					ps.close();
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}

			/* Checking whether this car is hybrid or not */
			if (fuels.size() > 1) {
				sql = "UPDATE VEHICLE SET Is_hybrid = 'TRUE' WHERE Reg_num = " + regNum;
				try {
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				sql = "UPDATE VEHICLE SET Is_hybrid = 'FALSE' WHERE Reg_num = " + regNum;
				try {
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			/* the data of color */
			deleteColorData(regNum, st);
			for (int color : colors) {
				sql = "INSERT INTO PAINTS (Reg_num, Color_num) VALUES (?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setInt(1, regNum);
					ps.setInt(2, color);
					ps.executeUpdate();
					ps.close();
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}

			conn.commit();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static void deleteFuelData(int regNum, Statement st) {
		String sql = "DELETE FROM USES WHERE Reg_num = " + Integer.toString(regNum);
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteColorData(int regNum, Statement st) {
		String sql = "DELETE FROM PAINTS WHERE Reg_num = " + Integer.toString(regNum);
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean changeShowing(String regNum) {

		if (conn == null)
			conn = DatabaseConnection.getConnection();

		try {
			Statement st = conn.createStatement();

			String sql = "";
			if (checkShowing(st, regNum)) {
				sql = "UPDATE VEHICLE SET Is_viewable = 'FALSE' WHERE Reg_num = " + regNum;
			} else {
				sql = "UPDATE VEHICLE SET Is_viewable = 'TRUE' WHERE Reg_num = " + regNum;
			}
			st.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean checkShowing(Statement st, String regNum) {
		ResultSet rs = null;
		String state = "";
		try {
			String sql = "SELECT Is_viewable FROM VEHICLE WHERE Reg_num = " + regNum;
			rs = st.executeQuery(sql);
			while (rs.next())
				state = rs.getString(1);
			if (state.equals("TRUE"))
				return true;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<Order> getOrderList() {
		String sql = "SELECT MAX(Order_num) FROM \"ORDER\"";
		int max = -1;
		
		if (conn == null)
			conn = DatabaseConnection.getConnection();

		if (orderList == null) {
			orderList = new ArrayList<Order>();
		} else {
			orderList.clear();
		}

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				max = rs.getInt(1);
			}
			
			sql = "SELECT * FROM \"ORDER\" ORDER BY Order_num DESC";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				orderList.add(new Order(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	
	public static boolean deleteCar(String regNum) {
		if (conn == null)
			conn = DatabaseConnection.getConnection();

		try {
			Statement st = conn.createStatement();
			String sql = "DELETE FROM VEHICLE WHERE REG_NUM=" + regNum;
			ResultSet resultset = st.executeQuery(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/* getter and setter */
}
