package managerPackage;

import managerPackage.ManagerAPIFromDB;
import managerPackage.ManagerView;

import java.util.InputMismatchException;
import java.util.Scanner;

import accountPackage.Account;
import mainPackage.main;

import java.io.IOException;
import java.sql.*;

public class ManagerTool {
	private ManagerAPIFromDB api = new ManagerAPIFromDB();
	private ManagerView mv = new ManagerView();
	private Scanner sc = new Scanner(System.in);

	private int login_account;
	private int theRegNum;
	private int command;

	public void index(Connection conn, Statement st, PreparedStatement ps) {
		try {
			api.setManagerNumber(login_account);
			do {
				mv.printCommand();
				try {
					command = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Input Error Try again");
					sc.nextLine();
					continue;
				}
				if (command > 11)
					continue;
				sc.nextLine();	// delete \n in the input 
				
				if (command == 1)
					api.createNewCar(conn, st, ps);
				else if (command == 2 || command == 3) {
					mv.printWhatCarModify();
					theRegNum = sc.nextInt();
					api.setOldRegNum(Integer.toString(theRegNum));
					sc.nextLine();	// delete \n in the input
					if (command == 2)
						api.updateCarInfo(conn, st, ps);
					else
						api.changeShowing(conn, st, ps);
				}
				else if (command == 4)
					Account.searchByName(conn, st, ps);
				else if (command == 5)
					api.getOrder(conn, st, ps);
				else if (command == 6)
					api.getOrderStats(conn, st, ps);
				else if(command == 7)
					Account.vehicleView(conn, st, ps);
				else if(command == 8)
					Account.searchAdvanced(conn, st, ps);
				else if(command == 9) {
					mv.printWhatCarToDelete();
					theRegNum = sc.nextInt();
					sc.nextLine();	// delete \n in the input
					api.setOldRegNum(Integer.toString(theRegNum));
					api.deleteVehicle(conn, st, ps);
				}
				else if (command == 10) {
					Account.log_Out();
					conn.commit();
					break;
				}
				else if (command == 11) {
					Account.log_Out();
					//conn.commit();
					main.set_Application_Finish();
					break;
				}
				
				System.out.println("\nComplete");
				System.out.println("Press the Enter to Go Back to Menu");
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

	public void setLogin_account(int get_loginAccount) {
		// TODO Auto-generated method stub
		this.login_account=get_loginAccount;
	}
}
