package accountPackage;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.sound.midi.Soundbank;

import vehiclePackage.Vehicle;
import managerPackage.ManagerView;

public class Account {
	public static boolean login_status = false;
	public static int login_account;
	public static String login_accounttype;
	public static String Id, Pw, Fname, Lname, Phonenumber, Email, Birthdate = "", Sex = "", Job = "", Address = "",
			Zipcode = "";

	public Account() {
		login_status = false;
		login_account = -1;
		login_accounttype = "";
	}

	public static boolean get_loginStatus() {
		return login_status;
	}

	public static String get_loginAccounttype() {
		return login_accounttype;
	}

	public static int get_loginAccount() {
		return login_account;
	}

	public static boolean login(Connection conn, Statement st, PreparedStatement ps) {
		Scanner sc = new Scanner(System.in);
		int count = 0;
		try {
			String sql = "SELECT PW, Lname,Account_num,User_type FROM ACCOUNT WHERE Id = ?";
			ps = conn.prepareStatement(sql);
			System.out.println("Login Process\n" + "Please Insert your Id");
			String Id = sc.next();
			ps.setString(1, Id);
			while (count < 3 && !login_status) {
				count++;
				ResultSet result = ps.executeQuery();
				System.out.println("Please Insert your password");
				String Pw = sc.next();
				if (result.next()) {
					if (Pw.equals(result.getString(1))) {
						System.out.println("Login Success");
						System.out.println("Welcome, " + result.getString(2));
						login_account = result.getInt(3);
						login_accounttype = result.getString(4);
						Lname=result.getString(2);
						login_status = true;
						break;
					} else if (count <= 1)
						System.out.println("Login Info is wrong try again");
				} else {
					System.out.println("Login info does not exists");
					return login_status;
				}

			}
			if (!login_status)
				System.out.println("Login trial limit exceeded try login process again");
		} catch (SQLException e) {
			System.out.println("Account Info does not exists");
		}
		return login_status;
	}

	public static void register(Connection conn, Statement st, PreparedStatement ps) {
		boolean id_test = false;
		int Account_num = -1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Register process");
		try {
			String sql = "SELECT Max(Account_num) FROM ACCOUNT";
			ResultSet resultset = st.executeQuery(sql);
			while (resultset.next())
				Account_num = resultset.getInt(1) + 1;
			sql = "SELECT * FROM ACCOUNT WHERE ID=?";
			ps = conn.prepareStatement(sql);

			while (!id_test) {
				System.out.println("Insert your Id");
				Id = sc.nextLine();
				ps.setString(1, Id);
				resultset = ps.executeQuery();
				if (resultset.next()) {
					System.out.println("Id already exists enter a new id");
					continue;
				} else
					id_test = true;
			}

			System.out.println("Insert your Pw");
			Pw = sc.nextLine();

			System.out.println("Insert your First name");
			Fname = sc.nextLine();
			System.out.println("Insert your Last name");
			Lname = sc.nextLine();
			System.out.println("Insert your Phone number(ex: 010-****-****)");
			Phonenumber = sc.nextLine();
			System.out.println("Insert your Email address");
			Email = sc.nextLine();
			System.out.println("To add more information insert Y otherwise just press enter");
			switch (sc.nextLine()) {
			case "Y":
			case "y":
				System.out.println("Insert your birthdate(ex: yyyy-mm-dd)");
				Birthdate = sc.nextLine();
				System.out.println("Insert your Sex(ex: M or F)");
				Sex = sc.nextLine();
				System.out.println("To add more information insert Y otherwise any character");
				switch (sc.nextLine()) {
				case "Y":
				case "y":
					System.out.println("Insert your job");
					Job = sc.nextLine();
					System.out.println("Insert your Address");
					Address = sc.nextLine();
					System.out.println("Insert your Zipcode(ex: *****)");
					Zipcode = sc.nextLine();
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}

			sql = "INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Account_num);
			if (Birthdate == "")
				ps.setNull(2, Types.DATE);
			else
				ps.setDate(2, java.sql.Date.valueOf(Birthdate));
			ps.setString(3, Id);
			ps.setString(4, Pw);
			ps.setString(5, Phonenumber);
			ps.setString(6, Email);
			if (Sex == "")
				ps.setNull(7, Types.VARCHAR);
			else
				ps.setString(7, Sex);
			if (Job == "")
				ps.setNull(8, Types.VARCHAR);
			else
				ps.setString(8, Job);
			ps.setString(9, "Buyer");
			ps.setString(10, Fname);
			ps.setString(11, Lname);
			if (Zipcode == "")
				ps.setNull(12, Types.VARCHAR);
			else
				ps.setString(12, Zipcode);
			if (Address == "")
				ps.setNull(13, Types.VARCHAR);
			else
				ps.setString(13, Address);

			int result = ps.executeUpdate();
			if (result == 0)
				System.out.println("Register success");
			conn.commit();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}

	}

	public static void change_Info(Connection conn, Statement st, PreparedStatement ps) {
		String temp_value;
		boolean first = true;
		account_Info(conn, st, ps);
		System.out.println(
				"Please insert the modified values for each fields (If you don't want to change just press enter)");

		String sql = "UPDATE ACCOUNT SET ";
		Scanner sc = new Scanner(System.in);
		System.out.print("Birthdate(ex:yyyy-mm-dd):");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			sql += "BIRTH_DATE=TO_DATE('" + temp_value + "', 'yyyy-mm-dd')";
			first = false;
		}
		System.out.print("Pw:");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "PW='" + temp_value + "'";
				first = false;
			} else
				sql += ", PW='" + temp_value + "'";
		}
		System.out.print("Phonenumber(ex:010-****-****):");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "PHONE='" + temp_value + "'";
				first = false;
			} else
				sql += ", PHONE='" + temp_value + "'";
		}
		System.out.print("Email:");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "EMAIL='" + temp_value + "'";
				first = false;
			} else
				sql += ", EMAIL='" + temp_value + "'";
		}
		System.out.print("Sex(ex:M or F):");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "SEX='" + temp_value + "'";
				first = false;
			} else
				sql += ", SEX='" + temp_value + "'";
		}
		System.out.print("Job:");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "JOB='" + temp_value + "'";
				first = false;
			} else
				sql += ", JOB='" + temp_value + "'";
		}
		System.out.print("Fname:");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "FNAME='" + temp_value + "'";
				first = false;
			} else
				sql += ", FNAME='" + temp_value + "'";
		}
		System.out.print("Lname:");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "LNAME='" + temp_value + "'";
				first = false;
			} else
				sql += ", LNAME='" + temp_value + "'";
		}
		System.out.print("Zipcode(ex:*****):");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "ZIPCODE='" + temp_value + "'";
				first = false;
			} else
				sql += ", ZIPCODE='" + temp_value + "'";
		}
		System.out.print("Address:");
		temp_value = sc.nextLine();
		if (!temp_value.equals("")) {
			if (first) {
				sql += "ADDR='" + temp_value + "'";
				first = false;
			} else
				sql += ", ADDR='" + temp_value + "'";
		}
		sql += " WHERE ACCOUNT_NUM=" + login_account;
		try {
			if (!first) {
				st = conn.createStatement();
				int result = st.executeUpdate(sql);
				if (result == 0)
					System.out.println("Change success");
				conn.commit();
			} else
				System.out.println("No change occured");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void log_Out() {
		login_status = false;
		login_account = -1;
	}

	public static void sign_Out(Connection conn, Statement st, PreparedStatement ps) {
		String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUM=" + login_account;
		try {
			ps = conn.prepareStatement(sql);
			int result = ps.executeUpdate();
			if (result == 0)
				System.out.println("Sign out success");
			log_Out();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void account_Info(Connection conn, Statement st, PreparedStatement ps) {

		String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUM=" + login_account;

		try {
			st = conn.createStatement();
			ResultSet result = st.executeQuery(sql);
			System.out.println("Current Account_num=" + login_account + " info");
			while (result.next()) {
				System.out.println("Account_num: " + result.getString(1));
				System.out.println("Birthdate: " + result.getDate(2));
				System.out.println("Id: " + result.getString(3));
				System.out.println("Pw: " + result.getString(4));
				System.out.println("Phone: " + result.getString(5));
				System.out.println("Email: " + result.getString(6));
				System.out.println("Sex: " + result.getString(7));
				System.out.println("Job: " + result.getString(8));
				System.out.println("User_type: " + result.getString(9));
				System.out.println("Fname: " + result.getString(10));
				System.out.println("Lname: " + result.getString(11));
				System.out.println("Zipcode: " + result.getString(12));
				System.out.println("Address: " + result.getString(13));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void searchByMake(Connection conn, Statement st, PreparedStatement ps) {
		Scanner sc = new Scanner(System.in);
		int make_num;
		String sql = "select v.reg_num, mk.make_name, m.model_name, d.detail_name "
				+ "from make mk, \"MODEL\" m, detailed_model d, vehicle v " + "where v.is_sold = \'FALSE\' "
				+ "and v.is_viewable = \'TRUE\' " + "and v.detailed_model = d.detail_num "
				+ "and m.model_num = d.parent_model " + "and mk.make_num = m.manufacturer " + "and mk.make_num = ? "
				+ "order by reg_num";
		String sql_make = "select mk.make_num, mk.make_name from make mk";

		System.out.println("Search Vehicles by Manufacturer.");
		try {
			/* show the make list */
			ps = conn.prepareStatement(sql_make);
			ResultSet rs = ps.executeQuery();
			System.out.println("NUM\t\tMAKE\t\t");
			System.out.println("--------------------------------");
			while (rs.next()) {
				System.out.println(format(Integer.toString(rs.getInt(1))) + format(rs.getString(2)));
			}
			System.out.print("Select the index of make: ");
			make_num = sc.nextInt();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, make_num);
			pageView(conn, st, ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void searchByName(Connection conn, Statement st, PreparedStatement ps) {
		Scanner sc = new Scanner(System.in);
		ManagerView mv = new ManagerView();

		/* the data of detailed model */
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		int makeIndex;
		int modelIndex;
		int detailedModelIndex;
		/*
		 * sql_all: query for searching all, sql_spec: qeury for searching specific
		 * vehicle
		 */
		String sql_all = "select v.reg_num, mk.make_name, m.model_name, d.detail_name "
				+ "from make mk, \"MODEL\" m, detailed_model d, vehicle v " + "where v.is_sold = \'FALSE\' "
				+ "and v.is_viewable = \'TRUE\' " + "and v.detailed_model = d.detail_num "
				+ "and m.model_num = d.parent_model " + "and mk.make_num = m.manufacturer " + "order by reg_num";
		String sql_spec = "select v.reg_num, mk.make_name, m.model_name, d.detail_name "
				+ "from make mk, \"MODEL\" m, detailed_model d, vehicle v " + "where v.is_sold = \'FALSE\' "
				+ "and v.is_viewable = \'TRUE\' " + "and v.detailed_model = d.detail_num "
				+ "and m.model_num = d.parent_model " + "and mk.make_num = m.manufacturer "
				+ "and v.detailed_model = ? " + "order by reg_num";
		String sql = null;

		indexList = mv.printMakeQuestion(conn, st, indexList);

		while (true) {
			System.out.println("\n0: Search for All");
			System.out.print("Input the index of brand: ");
			makeIndex = sc.nextInt();
			if (makeIndex > indexList.size()) {
				System.out.println("\nYour input value is not valid. Try again.\n");
				continue;
			}
			if (makeIndex != 0) {
				makeIndex = indexList.get(makeIndex - 1);
			}
			break;
		}

		try {

			if (makeIndex == 0) {
				sql = sql_all;
				ps = conn.prepareStatement(sql);
			} else {
				sql = sql_spec;
				ps = conn.prepareStatement(sql);

				indexList = mv.printModelQuestion(conn, st, makeIndex, indexList);

				while (true) {
					System.out.print("Input the index of model: ");
					modelIndex = sc.nextInt();
					if (modelIndex > indexList.size()) {
						System.out.println("\nYour input value is not valid. Try again.\n");
						continue;
					}
					modelIndex = indexList.get(modelIndex - 1);
					break;
				}
				indexList = mv.printDetailedModelQuestion(conn, st, modelIndex, indexList);

				while (true) {
					System.out.print("Input the index of detailed model: ");
					detailedModelIndex = sc.nextInt();
					if (detailedModelIndex > indexList.size()) {
						System.out.println("\nYour input value is not valid. Try again.\n");
						continue;
					}
					detailedModelIndex = indexList.get(detailedModelIndex - 1);
					break;
				}

				ps.setInt(1, detailedModelIndex);
			}

			pageView(conn, st, ps);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void searchAdvanced(Connection conn, Statement st, PreparedStatement ps) {
		Scanner sc = new Scanner(System.in);

		String str = null;
		ArrayList<String> option = new ArrayList<>();
		int count = 0;
		boolean terminator = false;

		String sql = "select v.reg_num, mk.make_name, m.model_name, d.detail_name";
		String from = "from (((make mk join \"MODEL\" m on mk.make_num = m.manufacturer) "
				+ "join detailed_model d on m.model_num = d.parent_model) "
				+ "join vehicle v on d.detail_num = v.detailed_model)";
		String where = "where v.is_sold = \'FALSE\' and v.is_viewable = \'TRUE\' ";
		String column = null;

		do {
			System.out.println("Advanced Search");
			System.out.println("Our system supports lexical search.");
			System.out.println("/help : get informations for search options");
			System.out.println("/z : go back to the menu");
			str = sc.nextLine();
			if (str.equals("/help")) {
				System.out.println("options:");
				System.out.println("Make: name of the manufacturer");
				System.out.println("Model: name of the model");
				System.out.println("Detail: name of the detailed model");
				System.out.println("Transmission: type of the transmission (auto, manual, semi-auto, cvt)");
				System.out.println("Condition: the vehicle condition (exellent, good, fair, poor)");
				System.out.println(
						"Category: category of the vehicle (Small, Semi-medium, Medium, Semi-large, Large, Sport-car, SUV, Etc)");
				System.out.println("Color: the vehicle color(s) (black, " + "blue, " + "blue-green, " + "brown, "
						+ "dark-gray, " + "etc, " + "galactic, " + "gold, " + "green, " + "light-gold, "
						+ "light-gray, " + "light-green, " + "orange, " + "pearl, " + "pink, " + "purple, " + "red, "
						+ "reed, " + "silver, " + "silver-gray, " + "skyblue, " + "white, " + "wine, " + "yellow, "
						+ "yellow-green)");
				System.out.println("Fuel: the fuel(s) used by vehicle (gasoline, " + "diesel, " + "lpg, "
						+ "electronic, " + "cng, " + "hydrogen)");
				System.out.println("Displacement: the endgine displacement (500, " + "1000, " + "1500, " + "2000, "
						+ "2500, " + "3000, " + "3500, " + "4000, " + "9999)");
				System.out.println("format: \'Option1:A\' + \'Option2:B/C\'");
				System.out.println("Please be careful of the use of upper and lower case letters!");
				System.out.println("Ex) Category:SUV+Color:black/white+Fuel:gasoline\n");
			} else if (str.equals("/z"))
				terminator = true;
		} while (str.equals("/help"));

		if (!terminator) {
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

				where += "and " + column + " in (\'" + detail_token.nextToken() + "\'";
				while (detail_token.hasMoreTokens())
					where += ", " + "\'" + detail_token.nextToken() + "\'";
				where += ") ";
			}
			if (!terminator) {
				sql += " " + from + " " + where + "order by v.reg_num asc ";
				try {
					// search all registered vehicles with advanced options
					ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					pageView(conn, st, ps);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void vehicleView(Connection conn, Statement st, PreparedStatement ps) {
		Scanner input = new Scanner(System.in);
		Vehicle vehicle = new Vehicle();
		vehicle.getVehicle(conn, ps);
		if (Account.get_loginAccounttype().equals("Seller") || vehicle.getIs_viewable()) {
			vehicle.printVehicle();
			if (Account.get_loginAccounttype().equals("Buyer") && !vehicle.getIs_sold()) {
				System.out.println("Wanna buy?(y/n): ");
				if (input.nextLine().equals("y")) {
					if (vehicle.purchase(conn))
						System.out.println("It is now your vehicle!");
					else
						System.out.println("Purchasing failed");
				} else
					System.out.println("Maybe next time!");
			}
		}
		else 
			System.out.println("There is no vehicle or not viewable by admin.");
	}

	public static void pageView(Connection conn, Statement st, PreparedStatement ps) throws SQLException {
		try {
			Scanner sc = new Scanner(System.in);
			int index = 0;
			ResultSet rs = ps.executeQuery();

			System.out.println("Search Result");

			System.out.println("NUM\t\tMAKE\t\tMODEL\t\tDETAIL");
			System.out.println("-------------------------------------");
			while (rs.next()) {
				index++;
				if (index % 30 == 0) {
					System.out.println("To see more pages press enter otherwise 'n'");
					if (sc.nextLine().equals("n"))
						break;
					System.out.println("NUM\t\tMAKE\t\tMODEL\t\tDETAIL");
					System.out.println("-------------------------------------------------------------------");

				}
				System.out.println(format(Integer.toString(rs.getInt(1))) + " " + format(rs.getString(2)) + " "
						+ format(rs.getString(3)) + " " + format(rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String format(String string) {
		return String.format("%0$-15s", string);
	}

}