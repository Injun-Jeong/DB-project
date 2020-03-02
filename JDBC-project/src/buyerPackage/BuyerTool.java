package buyerPackage;

import java.io.IOException;
import java.sql.*;

import buyerPackage.BuyerAPIFromDB;
import buyerPackage.BuyerView;

import java.util.InputMismatchException;
import java.util.Scanner;

import accountPackage.Account;
import mainPackage.main;

public class BuyerTool {
	private BuyerAPIFromDB api = new BuyerAPIFromDB();
	private BuyerView bv = new BuyerView();
	private Scanner sc = new Scanner(System.in);

	private int login_account;
	private int command;
	private String input;

	public void index(Connection conn, Statement st, PreparedStatement ps) {
		try {
			api.setBuyerNumber(login_account);
			do {
				bv.printCommand();
				input= sc.nextLine();
				
				if(input.length()==1 && input.charAt(0)>='1' && input.charAt(0)<='9')
					command=Integer.parseInt(input);
				else {
					System.out.println("Input error, Try again");
					continue;
				}
				if (command == 1)
					Account.change_Info(conn, st, ps);
				else if (command == 2)
					api.getOrder(conn, st, ps);
				else if (command == 3)
					Account.searchByMake(conn, st, ps);
				else if (command == 4) 
					Account.searchByName(conn,st,ps);
				else if(command==5)
					Account.searchAdvanced(conn, st, ps);
				else if (command == 6) {
					Account.vehicleView(conn, st, ps);
					conn.commit();
				} else if (command == 7) {
					Account.log_Out();
					conn.commit();
					break;
				} else if (command == 8) {
					Account.sign_Out(conn, st, ps);
					conn.commit();
					break;
				}
				else if(command==9) {
					Account.log_Out();
					conn.commit();
					main.set_Application_Finish();
					break;
				}
				
				System.out.println("Complete");
				System.out.println("Press Any Key to Go Back to Menu");
				sc.nextLine();
			} while (true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* getter and setter */
	public int getLogin_account() {
		return login_account;
	}

	public void setLogin_account(int login_account) {
		this.login_account = login_account;
	}
}
