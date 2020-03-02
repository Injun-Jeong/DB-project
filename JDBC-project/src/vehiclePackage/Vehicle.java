package vehiclePackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import accountPackage.Account;

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

	public void getVehicle(Connection conn, PreparedStatement ps) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);

		String sql1 = "select mk.make_name, mk.nation, m.model_name, d.detail_name, "
				+ "v.reg_num, t.transmission_type, to_char(v.release_date, \'yyyy-mm-dd\'), v.vin, cond.grade, v.is_sold, ed.ed_value, v.price, ca.category_name, v.is_hybrid, v.mpg, v.mileage , v.is_viewable "
				+ "from make mk, \"MODEL\" m, detailed_model d, vehicle v, transmission t, condition cond, category ca, engine_displacement ed "
				+ "where v.reg_num = ? " + "and mk.make_num = m.manufacturer " + "and m.model_num = d.parent_model "
				+ "and d.detail_num = v.detailed_model " + "and v.transmission = t.transmission_num "
				+ "and v.condition = cond.condition_num " + "and v.category = ca.category_num "
				+ "and v.displacement = ed.ed_num";

		String sql2 = "select c.color_name " + "from color c, paints p, vehicle v " + "where v.reg_num = ? "
				+ "and p.reg_num = v.reg_num " + "and c.color_num = p.color_num";

		String sql3 = "select f.fuel_type " + "from fuel f, uses u, vehicle v " + "where v.reg_num = ? "
				+ "and u.reg_num = v.reg_num " + "and f.fuel_num = u.fuel_num";
		
		ResultSet rs = null;
		
		try {
			while (true) {
				System.out.println("Select Reg# of the vehicle: ");
				this.reg_num = Integer.parseInt(input.nextLine());

				// load the list of manufacturers
				ps = conn.prepareStatement(sql1);
				ps.setInt(1, reg_num);

				rs = ps.executeQuery();
				while (rs.next()) {
					this.make = rs.getString(1);
					this.nation = rs.getString(2);
					this.model = rs.getString(3);
					this.detail = rs.getString(4);
					this.reg_num = rs.getInt(5);
					this.transmission = rs.getString(6);
					this.release_date = rs.getString(7);
					this.VIN = rs.getString(8);
					this.condition = rs.getString(9);
					if (rs.getString(10).equals("TRUE"))
						this.is_sold = true;
					else
						this.is_sold = false;
					this.displacement = rs.getInt(11);
					this.price = rs.getInt(12);
					this.category = rs.getString(13);
					if (rs.getString(14).equals("TRUE"))
						this.is_hybrid = true;
					else
						this.is_hybrid = false;
					this.mpg = rs.getInt(15);
					this.mileage = rs.getInt(16);
					if (rs.getString(17).equals("TRUE"))
						this.is_viewable = true;
					else
						this.is_viewable = false;
				}
				if (this.VIN == null) {
					System.out.println("There is no corresponding vehicle, try again");
					continue;
				}
				break;
			}
			
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, reg_num);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				this.Color.add(rs.getString(1));
			}
			
			ps = conn.prepareStatement(sql3);
			ps.setInt(1, reg_num);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				this.Fuel.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void printVehicle() {
		System.out.println("reg#: " + this.reg_num);
		System.out.println("Manufacturer: " + this.make);
		System.out.println("Model: " + this.model);
		System.out.println("Detailed Model: " + this.detail);
		System.out.println("Nation: " + this.nation);
		System.out.println("Condition: " + this.condition);
		System.out.println("Transmission: " + this.transmission);
		System.out.println("Release date: " + this.release_date);
		System.out.println("Is_sold: " + this.is_sold);
		System.out.println("Displacement: " + this.displacement);
		System.out.println("Price: " + this.price);
		System.out.println("Category: " + this.category);
		System.out.println("Is_hybrid: " + this.is_hybrid);
		System.out.println("Mpg: " + this.mpg);
		System.out.println("Mileage: " + this.mileage);
		System.out.println("VIN: " + this.VIN);
		System.out.println("Color: " + this.Color);
		System.out.println("Fuel: " + this.Fuel);
		if (Account.get_loginAccounttype().equals("Seller"))
			System.out.println("Is_Viewable: " + this.is_viewable);

		/*
		 * for(String color: this.Color) System.out.print(color); System.out.println();
		 * System.out.print("Color: "); for(String color: this.Color)
		 * System.out.print(color); System.out.println();
		 */
	}

	public boolean purchase(Connection conn) {
		boolean res = false;
		try {
			CallableStatement cs = conn.prepareCall("{call buyingProcess(?,?)}");
			cs.setInt(1, this.reg_num);
			cs.setInt(2, Account.get_loginAccount());
			res = cs.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean getIs_sold() {
		return this.is_sold;
	}

	public boolean getIs_viewable() {
		return this.is_viewable;
	}
}