package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import managerPackage.*;
import buyerPackage.*;
import accountPackage.Account;

public class main {
	public static final String URL = "jdbc:oracle:thin:@155.230.36.61:1521:orcl"; // Oracle
																														// Address
	public static final String USER_NAME = "s2017110115"; // Oracle user ID
	public static final String USER_PASSWD = "qaz123wsx456"; // Oracle user password
	public static boolean application_continue = true;

	public static void main(String[] args) {
		Connection conn = null;

		/* Connect to oracle */
		try {
			String sql = "SELECT * FROM ACCOUNT";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD);
			PreparedStatement ps = conn.prepareStatement(sql);
			Statement st = conn.createStatement();
			HomeTool ht = new HomeTool();
			ManagerTool mt = new ManagerTool();
			BuyerTool bt = new BuyerTool();

			while (application_continue) { // ȸ�� ���� �� �α��� ���� if
				if (!Account.get_loginStatus()) {
					ht.index(conn, st, ps);
				} else {
					if ((Account.get_loginAccounttype().equals("Buyer"))) {
						bt.setLogin_account(Account.get_loginAccount());
						bt.index(conn, st, ps);
					}

					else if (Account.get_loginAccounttype().equals("Seller")) {
						mt.setLogin_account(Account.get_loginAccount());
						mt.index(conn, st, ps);
					}
				}

			}

			System.out.println("Application closing...");
			conn.commit();
			st.close();
			ps.close();
			System.out.println("Connection terminating...");
			conn.close();
			System.out.println("Application closed");
		}

		catch (ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		} catch (SQLException ex) {
			System.err.println("Cannot close" + ex.getMessage());
			System.exit(1);
		}
	}

	public static void set_Application_Finish() {
		// TODO Auto-generated method stub
		application_continue = false;
	}
}