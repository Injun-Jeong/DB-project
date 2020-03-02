package buyerPackage;
import java.sql.*; // import JDBC package
import java.util.Scanner;

import accountPackage.Account;

public class BuyerAPIFromDB {
	/* attributes */
	private int accountNum;
	public void getOrder(Connection conn, Statement st, PreparedStatement ps) {
		try {
			String sql = "SELECT * FROM \"ORDER\" WHERE BUYER=" + accountNum+" ORDER BY ORDER_DATE DESC";
			ResultSet res = st.executeQuery(sql);

			if (res.next()) {
				System.out.println("ORDER Information");
				System.out.println("Order_num\tOrder_date\tBuyer\tSeller\tReg_num");
				System.out.println("---------------------------------------------------------");
				do{
					System.out.println(res.getInt(1) + "\t\t" + res.getDate(2) + "\t" + res.getInt(3) + "\t"
							+ res.getInt(4) + "\t" + res.getInt(5) + "\t");
				}while(res.next());
			} else
				System.out.println("You don't have order records\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* getter and setter */
	public int getBuyerNumber() {
		return accountNum;
	}

	public void setBuyerNumber(int accountNum) {
		this.accountNum = accountNum;
	}
}
