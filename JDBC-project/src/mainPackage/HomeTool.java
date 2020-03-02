package mainPackage;

import java.util.Scanner;

import accountPackage.Account;
import mainPackage.main;

import java.io.IOException;
import java.sql.*;

public class HomeTool {
	private HomeView hv = new HomeView();
	private Scanner sc = new Scanner(System.in);
	private String input;
	private int command;

	public void index(Connection conn, Statement st, PreparedStatement ps) {
		try {
			do {
				hv.printCommand();
				
				input= sc.nextLine();
				if(input.length()==1 && input.charAt(0)>='1' && input.charAt(0)<='3')
					command=Integer.parseInt(input);
				else {
					System.out.println("Input error, Try again");
					continue;
				}
				if (command == 1) {
					Account.login(conn,st,ps);
					break;
				}
				else if (command == 2){
					Account.register(conn, st, ps);
					conn.commit();
				}
				else if (command == 3){
					Account.log_Out();
					conn.commit();
					main.set_Application_Finish();
					break;
				}
				System.out.println("Complete");
				
			} while (true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
