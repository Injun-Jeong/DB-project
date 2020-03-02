package vehiclePackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import accountPackage.Account;
import util.DatabaseConnection;

public class Vehicle {
	int reg_num;
	String make;
	String nation;
	String model;
	String detail;
	String transmission;
	String release_date;
	String VIN;
	String condition;
	boolean is_sold;
	int displacement;
	int price;
	String category;
	boolean is_hybrid;
	int mpg;
	int mileage;
	ArrayList<String> Color;
	ArrayList<String> Fuel;
	boolean is_viewable;

	public Vehicle() {
		this.Color = new ArrayList<>();
		this.Fuel = new ArrayList<>();
	}
	
	public static ArrayList<String> getMakeList(Connection conn) {
		String sql = "select mk.make_name from make mk";
		ArrayList<String> MakeList = new ArrayList<>();
		try {
			/* show the make list */
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MakeList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MakeList;
	}
public static ArrayList<briefVehicle> searchByMake(Connection conn,String makeName) {
	String sql = "select distinct v.reg_num, mk.make_name, m.model_name, d.detail_name, c.category_name, v.price,v.is_sold, v.release_date, v.mileage\n" + 
			"from vehicle v, detailed_model d, \"MODEL\" m, make mk, \"CATEGORY\" c\n" + 
			"where mk.make_name=? \n" +
			"and is_viewable = 'TRUE'\n" + 
			"and v.detailed_model = d.detail_num\n" + 
			"and d.parent_model = m.model_num\n" + 
			"and m.manufacturer = mk.make_num\n" + 
			"and v.category = c.category_num\n" + 
			"and v.is_sold = 'FALSE'\n" +
			"order by reg_num";

	ArrayList<briefVehicle> VehicleList =new ArrayList<briefVehicle>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, makeName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VehicleList.add(new briefVehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7), rs.getDate(8).toString(), rs.getInt(9)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return VehicleList;
	}
	
	public static ArrayList<briefVehicle> searchByName(Connection conn, String name) {
		String sql = null;
		sql = "select distinct v.reg_num, mk.make_name, m.model_name, d.detail_name, c.category_name, v.price,v.is_sold, v.release_date, v.mileage\n" + 
				"from vehicle v, detailed_model d, \"MODEL\" m, make mk, \"CATEGORY\" c\n" + 
				"where v.detailed_model in ((\n" + 
				"select d1.detail_num\n" + 
				"from detailed_model d1\n" + 
				"where d1.detail_name like '%"+name+"%')\n" + 
				"union(\n" + 
				"select d2.detail_num\n" + 
				"from detailed_model d2, \"MODEL\" m1\n" + 
				"where m1.model_name like '%"+name+"%'\n" + 
				"and m1.model_num = d2.parent_model))\n" + 
				"and is_viewable = 'TRUE'\n" + 
				"and v.detailed_model = d.detail_num\n" + 
				"and d.parent_model = m.model_num\n" + 
				"and m.manufacturer = mk.make_num\n" + 
				"and v.category = c.category_num\n" + 
				"and v.is_sold = 'FALSE'\n" +
				"order by reg_num";
		
		ArrayList<briefVehicle> VehicleList = new ArrayList<>();
		try {
			/* show the make list */
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VehicleList.add(new briefVehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7), rs.getDate(8).toString(), rs.getInt(9)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return VehicleList;
	}

	public static ArrayList<briefVehicle> searchAdvanced(String str) {
		ArrayList<String> option = new ArrayList<>();
		ArrayList<briefVehicle> VehicleList = new ArrayList<>();
		int count = 0;
		String sql = "select distinct v.reg_num, mk.make_name, m.model_name, d.detail_name, ca.category_name, v.price,v.is_sold, v.release_date, v.mileage\n";
		String from = "from (((make mk join \"MODEL\" m on mk.make_num = m.manufacturer) "
				+ "join detailed_model d on m.model_num = d.parent_model) "
				+ "join vehicle v on d.detail_num = v.detailed_model)";
		String where = "where v.is_sold = \'FALSE\' and v.is_viewable = \'TRUE\' ";
		String column = null;
		
				
			StringTokenizer option_token = new StringTokenizer(str, "+");
			while (option_token.hasMoreTokens()) {
				option.add(option_token.nextToken());
				StringTokenizer detail_token = new StringTokenizer(option.get(count++), ":/");
				// ArrayList<String> detail = new ArrayList<>();
				// detail.add(detail_token.nextToken());
				switch (detail_token.nextToken()) {
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
					break;
				case "Condition":
					from += ", condition cond";
					where += "and v.condition = cond.condition_num ";
					column = "cond.grade";
					break;
				case "Category":

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
				}

				where += "and " + column + " in (\'" + detail_token.nextToken() + "\'";
				while (detail_token.hasMoreTokens())
					where += ", " + "\'" + detail_token.nextToken() + "\'";
				where += ") ";
			}

			from += ", category ca";
			where += "and v.category = ca.category_num ";
			sql += " " + from + " " + where + "order by v.reg_num asc ";
			try {
				Connection conn=DatabaseConnection.getConnection();
				PreparedStatement ps=conn.prepareStatement(sql);
				// search all registered vehicles with advanced options
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					VehicleList.add(new briefVehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7), rs.getDate(8).toString(), rs.getInt(9)));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return VehicleList;
		}

	public boolean getIs_sold() {
		return this.is_sold;
	}

	public boolean getIs_viewable() {
		return this.is_viewable;
	}
	
}