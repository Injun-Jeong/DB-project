package managerPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ManagerView {
	
	public void printCommand() {
		System.out.println(
				"\nHello, manager.\nPlease select the wanted command.\n"
				+ "1. Post new car for sale\n"
				+ "2. Modified the information of registered car\n"
				+ "3. Change the status of showing\n"
				+ "4. Search Vehicles By Name\n"
				+ "5. See all history of orders\n"
				+ "6. See the statistics of orders\n"
				+ "7. View the detailed information of a vehicle\n"
				+ "8. Advanced Search\n"
				+ "9. Delete existed vehicle\n"
				+ "10. Logout\n"
				+ "11. Close Application");
		System.out.print("Input: ");
	}

	public void printWhatCarToDelete() {
		System.out.println("\nPlease input the reg_num of car you want to delete.");
		System.out.print("Input: ");
	}
	
	public void printWhatCarModify() {
		System.out.println("\nPlease input the reg_num of car you want to modify.");
		System.out.print("Input: ");
	}
	
	public void printTransmissionQuestion() {
		System.out.println("\nWhat kind of transmission is your car?");
		System.out.println("1. Auto\n2. Manual\n3. Semi-auto\n4. CVT");
		System.out.print("Input the index of transmission: ");
	}
	
	public void printReleaseDateQuestion() {
		System.out.println("\nWhen is the vehicle released?");
		System.out.println("(The format of input is MM/YYYY)");
		System.out.print("Input: ");
	}
	
	public void printVinQuestion() {
		System.out.println("\nWhat is the VIN of your car? (six digits)");
		System.out.print("Input: ");
	}
	
	public void printConditionQuestion() {
		System.out.println("\nWhat is the condition of your car?");
		System.out.println("1. Excellent\n2. Good\n3. Fair\n4. Poor");
		System.out.print("Input the index of condition: ");
	}
	
	public void printEngineDisplacementQuestion() {
		System.out.println("\nWhat is the engine displacement of your car?");
		System.out.println(
				"1. less than 500\n2. less than 1000\n3. less than 1500\n4. less than 2000" +
				"\n5. less than 2500\n6. less than 3000\n7. less than 3500\n8. less than 4000\n9. more or equal than 4000");
		System.out.print("Input the index of engine displacement: ");
	}
	
	public void printPriceQuestion() {
		System.out.println("\nHow much do you want for the sale price?\n(Unit is 10,000won)");
		System.out.print("Input the asking price: ");
	}
	
	public ArrayList<Integer> printMakeQuestion(Connection conn, Statement stmt, ArrayList<Integer> indexList) {
		System.out.println("\nWhat is the brand of your car?");
		indexList = makeModelCarList(conn, stmt, "MAKE", "", indexList);
		return indexList;
	}
	
	public ArrayList<Integer> printModelQuestion(Connection conn, Statement stmt, int makeIndex, ArrayList<Integer> indexList) {
		System.out.println("\nWhat is the model of your car?");
		indexList = makeModelCarList(conn, stmt, "MODEL", Integer.toString(makeIndex), indexList);
		return indexList;
	}
	
	public ArrayList<Integer> printDetailedModelQuestion(Connection conn, Statement stmt, int modelIndex, ArrayList<Integer> indexList) {
		System.out.println("\nWhat is the detailed model of your car?");
		indexList = makeModelCarList(conn, stmt, "DETAILED_MODEL", Integer.toString(modelIndex), indexList);
		return indexList;
	}
	
	public void printCategoryQuestion() {
		System.out.println("\nWhat kind of category is your car?");
		System.out.println("1. Small\n2. Semi-medium\n3. Medium\n4. Semi-large\n5. Large\n6. Sport-car\n7. SUV\n8. Etc");
		System.out.print("Input the index of category: ");
	}
	
	public void printMPGQuestion() {
		System.out.println("\nHow much does the MPG of your car?\n(Unit is km/L)");
		System.out.print("Input the MPG: ");
	}
	
	public void printMileageQuestion() {
		System.out.println("\nHow much does the mileage of your car?\n(Unit is km)");
		System.out.print("Input the mileage: ");
	}
	
	public void printFuelQuestion() {
		System.out.println("\nWhat kind of fuel is your car?");
		System.out.println("1. Gasoline\n2. Diesel\n3. LPG\n4. Electronic\n5. CNG\n6. Hydrogen");
		System.out.println("Input the index of fuel: ");
	}
	
	public void printColorQuestion() {
		System.out.println("\nWhat color is your car?");
		System.out.println(
			"1. White\n2. Pearl\n3. Black\n4. Blue\n5. Dark-gray\n6. Silver" +
			"\n7. Silver-gray\n8. Skyblue\n9. Light-green\n10. Blue-green\n11. Galactic\n12. Light-gray" +
			"\n13. Red\n14. Orange\n15. Wine\n16. Purple\n17. Pink\n18. Yellow" +
			"\n19. Reed\n20. Light-gold\n21. Brown\n22. Green\n23. Gold\n24. Yellow-green\n25. etc");
		System.out.println("Input the index of color: ");
	}
	
	public ArrayList<Integer> makeModelCarList(Connection conn, Statement stmt, String tableName, String constraint, ArrayList<Integer> indexList) {
		indexList.clear();

		ResultSet rs = null;
		String sql = "";
		try {
			if (tableName.equals("MAKE")) {
				sql =
					"SELECT Make_num, Make_name " +
					"FROM MAKE";
			} else if (tableName.equals("MODEL")) {
				sql =
					"SELECT Model_num, Model_name " +
					"FROM MODEL " +
					"WHERE Manufacturer=" + constraint;
			} else if (tableName.equals("DETAILED_MODEL")) {
				sql =
					"SELECT Detail_num, Detail_name " +
					"FROM DETAILED_MODEL " +
					"WHERE Parent_Model=" + constraint;
			}			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				indexList.add(rs.getInt(1));
				String tupleName = rs.getString(2);
				System.out.println(Integer.toString(indexList.size()) + ": " + tupleName);
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return indexList;
	}
}
