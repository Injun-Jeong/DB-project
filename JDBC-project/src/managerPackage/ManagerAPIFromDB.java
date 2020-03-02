package managerPackage;

import managerPackage.ManagerView;
import java.math.BigDecimal;
import java.sql.*; // import JDBC package
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;

public class ManagerAPIFromDB {
	/* attributes */
	private Scanner sc = new Scanner(System.in);
	private ArrayList<String> inputData = new ArrayList<String>();
	private int accountNum;
	private int newRegNum;
	private String oldRegNum;
	private ManagerView mv = new ManagerView();
	boolean inputValid;
	private ArrayList<Integer> indexList = new ArrayList<Integer>();

	/* method */
	/* To create new car for sale by manager - COMPLETE */
	public void createNewCar(Connection conn, Statement stmt, PreparedStatement ps) {
		setNewRegNum(checkNewIndex(stmt, "VEHICLE"));
		try {
			parsingInputData(conn, stmt, ps, 1);
			String sql = "INSERT INTO VEHICLE "
					+ "(Reg_num,Transmission,Release_date,Vin,Condition,Displacement,Price,Detailed_model,Registerer,Category,MPG,Mileage) "
					+ "VALUES (" + Integer.toString(newRegNum) + "," + inputData.get(0) + ",TO_DATE('" // transmission
					+ inputData.get(1) + "','mm/yyyy'),'" // release date
					+ inputData.get(2) + "'," // vin
					+ inputData.get(3) + "," // condition
					+ inputData.get(4) + "," // displacement
					+ inputData.get(5) + "," // price
					+ inputData.get(6) + "," // detailed_model
					+ inputData.get(7) + "," // register
					+ inputData.get(8) + "," // category
					+ inputData.get(9) + "," // mpg
					+ inputData.get(10) + ")"; // mileage
			stmt.executeUpdate(sql);

			/* the data of fuel */
			inputValid = false;
			while (!inputValid) {inputValid = inputFuelData(conn, ps, this.newRegNum);}
			if (checkMoreThanOne(conn, stmt, "USES", Integer.toString(newRegNum))) {
				sql = "UPDATE VEHICLE SET Is_hybrid = 'TRUE' WHERE Reg_num = " + Integer.toString(this.newRegNum);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			/* the data of color */			
			inputValid = false;
			while (!inputValid) {inputValid = inputColorData(conn, ps, this.newRegNum);}
			
			conn.commit();
		} catch (SQLException ex2) {
			System.out.println("sql error = " + ex2.getMessage());
		}
	}

	/* To update the information of registered car by manager - COMPLETE */
	public void updateCarInfo(Connection conn, Statement stmt, PreparedStatement ps) {
		try {
			parsingInputData(conn, stmt, ps, 2);
			String sql = "UPDATE VEHICLE SET " + "Transmission = " + inputData.get(0) + ", Release_date = TO_DATE('"
					+ inputData.get(1) + "','mm/yyyy')" + ", Condition = " + inputData.get(2) + ", Displacement = "
					+ inputData.get(3) + ", Price = " + inputData.get(4) + ", Detailed_model = " + inputData.get(5)
					+ ", Registerer = " + inputData.get(6) + ", Category = " + inputData.get(7) + ", MPG = "
					+ inputData.get(8) + ", Mileage = " + inputData.get(9) + " WHERE Reg_num = " + this.oldRegNum;
			stmt.executeUpdate(sql);

			/* the data of fuel */
			deleteFuelData(stmt);
			boolean inputValid = false;
			while (!inputValid) {inputValid = inputFuelData(conn, ps, Integer.parseInt(this.oldRegNum));}
			if (checkMoreThanOne(conn, stmt, "USES", this.oldRegNum)) {
				sql = "UPDATE VEHICLE SET Is_hybrid = 'TRUE' WHERE Reg_num = " + this.oldRegNum;
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				sql = "UPDATE VEHICLE SET Is_hybrid = 'FALSE' WHERE Reg_num = " + this.oldRegNum;
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			/* the data of color */			
			deleteColorData(stmt);
			inputValid = false;
			while (!inputValid) {inputValid = inputColorData(conn, ps, Integer.parseInt(this.oldRegNum));}

			conn.commit();
		} catch (SQLException ex2) {
			System.out.println("sql error = " + ex2.getMessage());
		}
	}

	/* To change the state of showing by manager - COMPLETE */
	public void changeShowing(Connection conn, Statement stmt, PreparedStatement ps) {
		String sql = "";
		if (checkShowing(stmt)) {
			sql = "UPDATE VEHICLE SET Is_viewable = 'FALSE' WHERE Reg_num = " + this.oldRegNum;
		} else {
			sql = "UPDATE VEHICLE SET Is_viewable = 'TRUE' WHERE Reg_num = " + this.oldRegNum;
		}
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * option 1. create new car 2. update registered car
	 */
	public void parsingInputData(Connection conn, Statement stmt, PreparedStatement ps, int option) {
		int inputNum;
		
		inputData.clear();
		/* the data of transmission */
		mv.printTransmissionQuestion();
		inputNum = sc.nextInt();
		while(inputNum > 4) {
			System.out.println("\nYour input value is not valid. Try again.");
			System.out.print("Input the index of transmission: ");
			inputNum = sc.nextInt();
		}
		inputData.add(Integer.toString(inputNum));
		sc.nextLine();

		/* the data of release date */
		mv.printReleaseDateQuestion();
		inputData.add(sc.nextLine());

		/* the data of vin */
		if (option == 1) {
			mv.printVinQuestion();
			inputData.add(sc.nextLine());
		}

		/* the data of condition */
		mv.printConditionQuestion();
		inputNum = sc.nextInt();
		while(inputNum > 4) {
			System.out.println("\nYour input value is not valid. Try again.");
			System.out.print("Input the index of condition: ");
			inputNum = sc.nextInt();
		}
		inputData.add(Integer.toString(inputNum));
		sc.nextLine();

		/* the data of engine displacement */
		mv.printEngineDisplacementQuestion();
		inputNum = sc.nextInt();
		while(inputNum > 9) {
			System.out.println("\nYour input value is not valid. Try again.");
			System.out.print("Input the index of engine displacement: ");
			inputNum = sc.nextInt();
		}
		inputData.add(Integer.toString(inputNum));
		sc.nextLine();

		/* the data of price */
		mv.printPriceQuestion();
		inputData.add(sc.nextLine());

		/* the data of detailed model */
		indexList = mv.printMakeQuestion(conn, stmt, indexList);
		int makeIndex;
		while(true) {
			System.out.print("Input the index of brand: ");
			makeIndex = sc.nextInt();
			if (makeIndex > indexList.size()) {
				System.out.println("\nYour input value is not valid. Try again.\n");
				continue;
			}
			makeIndex = indexList.get(makeIndex - 1);
			break;
		}

		indexList = mv.printModelQuestion(conn, stmt, makeIndex, indexList);
		int modelIndex;
		while(true) {
			System.out.print("Input the index of model: ");
			modelIndex = sc.nextInt();
			if (modelIndex > indexList.size()) {
				System.out.println("\nYour input value is not valid. Try again.\n");
				continue;
			}
			modelIndex = indexList.get(modelIndex - 1);
			break;
		}
		
		indexList = mv.printDetailedModelQuestion(conn, stmt, modelIndex, indexList);
		int detailedModelIndex;
		while(true) {
			System.out.print("Input the index of detailed model: ");
			detailedModelIndex = sc.nextInt();
			if (detailedModelIndex > indexList.size()) {
				System.out.println("\nYour input value is not valid. Try again.\n");
				continue;
			}
			detailedModelIndex = indexList.get(detailedModelIndex - 1);
			break;
		}

		inputData.add(Integer.toString(detailedModelIndex));

		/* the data of register */
		inputData.add(Integer.toString(this.accountNum));

		/* the data of category */
		sc.nextLine();
		mv.printCategoryQuestion();
		inputData.add(sc.nextLine());

		/* the data of MPG */
		mv.printMPGQuestion();
		inputData.add(sc.nextLine());

		/* the data of Mileage */
		mv.printMileageQuestion();
		inputData.add(sc.nextLine());
	}

	public int checkNewIndex(Statement stmt, String tableName) {
		ResultSet rs = null;
		int maxRegNum = 0;
		try {
			String sql = "SELECT MAX(Reg_num) FROM " + tableName;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maxRegNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxRegNum + 1;
	}

	public boolean checkShowing(Statement stmt) {
		ResultSet rs = null;
		String state = "";
		try {
			String sql = "SELECT Is_viewable FROM VEHICLE WHERE Reg_num = " + this.oldRegNum;
			rs = stmt.executeQuery(sql);
			while (rs.next())
				state = rs.getString(1);
			if (state.equals("TRUE"))
				return true;
			rs.close();
		} catch (SQLException e) {
			System.out.println("checking showing error");
		}
		return false;
	}

	public boolean inputFuelData(Connection conn, PreparedStatement ps, int regNum) {
		System.out.println("\nDoes the car have the type of fuel more than one? [y/n]");
		System.out.print("Enter the answer: ");
		int flag = inputYesOrNot();
		
		String fuelInsert = "";
		do {
			mv.printFuelQuestion();
			fuelInsert = "INSERT INTO USES " + "(Fuel_num, Reg_num) " + "VALUES (?,?)";

			try {
				ps = conn.prepareStatement(fuelInsert);
				ps.setInt(1, Integer.parseInt(sc.nextLine()));
				ps.setInt(2, regNum);
				ps.executeUpdate();
				ps.close();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Your input of fuel is index. Try again.");
				return false;
			}

			if (flag < 2) {
				flag++;
				continue;
			} else
				break;
		} while (true);
		
		return true;
	}
	
	public void deleteFuelData(Statement st) {
		String sql = "DELETE FROM USES WHERE Reg_num = " + oldRegNum;
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean inputColorData(Connection conn, PreparedStatement ps, int regNum) {
		System.out.println("\nDoes the car have colors more than one? [y/n]");
		System.out.print("Enter the answer: ");
		int flag = inputYesOrNot();

		String colorInsert = "";
		do {
			colorInsert = "INSERT INTO PAINTS " + "(Reg_num, Color_num) " + "VALUES (?,?)";
			mv.printColorQuestion();
			try {
				ps = conn.prepareStatement(colorInsert);
				ps.setInt(1, regNum);
				ps.setInt(2, Integer.parseInt(sc.nextLine()));
				ps.executeUpdate();
				ps.close();
				conn.commit();
			} catch (SQLException e) {
				System.out.println("Your input of color is index. Try again.");
				return false;
			}

			if (flag < 2) {
				flag++;
				continue;
			} else
				break;
		} while (true);
		
		return true;
	}
	
	public void deleteColorData(Statement st) {
		String sql = "DELETE FROM PAINTS WHERE Reg_num = " + oldRegNum;
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int inputYesOrNot() {
		while (true) {
			String input = sc.nextLine();
			switch (input) {
			case "Y":
			case "y":
				return 1;
			case "N":
			case "n":
				return 2;
			default:
				System.out.println("Try again.");
				System.out.print("Enter the answer: ");
				continue;
			}
		}
	}
	
	public boolean checkMoreThanOne(Connection conn, Statement stmt, String tableName, String rn) {
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE Reg_num = " + rn;
			rs = stmt.executeQuery(sql);
			while (rs.next()) count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count < 2) return false;
		else return true;
	}

	public void getOrder(Connection conn, Statement st, PreparedStatement ps) {
		try {
			Scanner sc = new Scanner(System.in);
			int ORDER_COUNT = -1;
			int cur_Count = 0;
			String sql = "SELECT COUNT(*) FROM \"ORDER\"";
			ResultSet res = st.executeQuery(sql);
			if (res.next())
				ORDER_COUNT = res.getInt(1);
			sql = "SELECT * FROM \"ORDER\"";
			res = st.executeQuery(sql);
			System.out.println("ORDER Information");
			System.out.println("Order_num\tOrder_date\tBuyer\tSeller\tReg_num");
			System.out.println("---------------------------------------------------------");
			String result;
			for (cur_Count = 1; cur_Count <= ORDER_COUNT && res.next(); cur_Count++) {
				result=null;
				cur_Count = res.getInt(1);
				if (cur_Count % 30 == 0) {
					System.out.println("To see more pages press enter otherwise enter 'n'");
					if (sc.nextLine().equals("n"))
						break;
					System.out.println("Order_num\t\tOrder_date\tBuyer\tSeller\tReg_num");
					System.out.println("---------------------------------------------------------");
				}
				result=cur_Count + "\t\t" + res.getDate(2) + "\t";
				if(res.getInt(3)==0)
					result+="Unknown";
				else
					result+=res.getInt(3);
				result+="\t" + res.getInt(4)+"\t";
				if(res.getInt(5)==0)
					result+="Unknown";
				else
					result+=res.getInt(5);
				System.out.println(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getOrderStats(Connection conn, Statement st, PreparedStatement ps) {
		System.out.println("Input the year to see the monthly status");
		String year=sc.nextLine();
		try {
			String sql = "SELECT TO_CHAR(Order_date,'mm'), SUM(Price)\n" + "FROM \"ORDER\" natural join Vehicle\n"
		+ "WHERE TO_CHAR(Order_date,'yyyy')="+year + "GROUP BY TO_CHAR(Order_date,'mm')\n" + "ORDER BY TO_CHAR(Order_date,'mm')";
			ResultSet res = st.executeQuery(sql);
			System.out.println("Montly ORDER Stats");
			System.out.println("Month\tSum");
			System.out.println("--------------");
			while (res.next()) {
				if(res.getString(1)!=null)
					System.out.println(res.getString(1) + "\t" + res.getInt(2));
			}

			sql = "SELECT TO_CHAR(Order_date,'yyyy'), SUM(Price)\n" + "FROM \"ORDER\" natural join Vehicle\n"
					+ "GROUP BY TO_CHAR(Order_date,'yyyy')\n" + "ORDER BY TO_CHAR(Order_date,'yyyy')";
			System.out.println("\n\nYearly ORDER Stats");
			System.out.println("Year\tSum");
			System.out.println("--------------");
			res = st.executeQuery(sql);
			while (res.next()) {
				System.out.println(res.getString(1) + "\t" + res.getInt(2));
			}
			sql = "SELECT make_name,sum(price)\n" + "FROM \"ORDER\" NATURAL JOIN VEHICLE,DETAILED_MODEL, MODEL, MAKE\n"
					+ "where detailed_model=detail_num\r\n" + "and parent_model=model_num\n"
					+ "and manufacturer=make_num\n" + "group by make_name";
			System.out.println("\n\nManufacturer ORDER Stats");
			System.out.println("Manufacturer\tSum");
			System.out.println("--------------------");
			res = st.executeQuery(sql);
			while (res.next()) {
				if (res.getString(1).length() >= 9)
					System.out.printf("%s\t%6d\n", res.getString(1), res.getInt(2));
				else
					System.out.printf("%s\t\t%6d\n", res.getString(1), res.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteVehicle(Connection conn, Statement st, PreparedStatement ps) {
		String sql = "DELETE FROM VEHICLE WHERE REG_NUM=" + this.oldRegNum;
		try {
			ps = conn.prepareStatement(sql);
			int result = ps.executeUpdate();
			if (result == 0)
				System.out.println("Delete the vehicle success");
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* getter and setter */
	public int getManagerNumber() {
		return accountNum;
	}

	public void setManagerNumber(int accountNum) {
		this.accountNum = accountNum;
	}

	public int getNewRegNum() {
		return newRegNum;
	}

	public void setNewRegNum(int newRegNum) {
		this.newRegNum = newRegNum;
	}

	public String getOldRegNum() {
		return oldRegNum;
	}

	public void setOldRegNum(String oldRegNum) {
		this.oldRegNum = oldRegNum;
	}

}
