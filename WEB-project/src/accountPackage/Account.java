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

import util.DatabaseConnection;

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
	public static String get_UserId() {
		return Id;
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
	public static String get_Accountname() {
		return Lname;
	}

	public static boolean login(String Id, String Pw) {
		try {
			String sql = "SELECT PW, Lname,Account_num,User_type FROM ACCOUNT WHERE Id = ?";
			Connection conn= DatabaseConnection.getConnection();
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, Id);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				if (Pw.equals(result.getString(1))) {
					login_status = true;
					Lname=result.getString(2);
					login_account = result.getInt(3);
					login_accounttype = result.getString(4);
					return true;
				}
			}
			else
				return false;
		} catch (SQLException e) {
			System.out.println("Account Info does not exists");
		}
		return false;
	}

	public static boolean register(String Id, String Pw, String Fname, String Lname, String Phonenumber, String Email, String Birthdate, String Sex, String Job, String Address,
			String Zipcode) {
		boolean id_test = false;
		int Account_num = -1;
		int result=-1;
		try {
			Connection conn=DatabaseConnection.getConnection();
			Statement st=conn.createStatement();
			PreparedStatement ps;
			String sql = "SELECT Max(Account_num) FROM ACCOUNT";
			ResultSet resultset = st.executeQuery(sql);
			while (resultset.next())
				Account_num = resultset.getInt(1) + 1;
			sql = "SELECT * FROM ACCOUNT WHERE ID=?";
			ps = conn.prepareStatement(sql);
			/*
			 * while (!id_test) { ps.setString(1, Id); resultset = ps.executeQuery(); if
			 * (resultset.next()) { System.out.println("Id already exists enter a new id");
			 * continue; } else id_test = true; }
			 */
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

			result = ps.executeUpdate();
			conn.commit();
			
			return true;
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static void log_Out() {
		login_status = false;
		login_account = -1;
	}

	public static boolean deleteAccount(String accountNum) {
		Connection conn = DatabaseConnection.getConnection();
		
		try {
			Statement st = conn.createStatement();
			String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUM=" + accountNum;
			ResultSet resultset = st.executeQuery(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean change_Info(String login_account, String Pw, String Phonenumber, String Email, String Job, String Address, String Zipcode) {
		String sql="UPDATE ACCOUNT SET ";
		sql+="PW='" + Pw+"'";
		if (!Phonenumber.equals(""))
			sql+=", PHONE='" + Phonenumber + "'";
		if (!Email.equals(""))
			sql+=", EMAIL='" + Email + "'";
		if (!Job.equals(""))
			sql+=", JOB='" + Job + "'";
		if (!Address.equals(""))
			sql+=", ADDR='" + Address + "'";
		if (!Zipcode.equals(""))
			sql+=(", ZIPCODE='" + Zipcode + "'");
		sql+=" WHERE ACCOUNT_NUM=" + login_account;

		try {
			Connection conn= DatabaseConnection.getConnection();
			Statement st=conn.createStatement();
			int result = st.executeUpdate(sql);
			if (result == 1) {
				conn.commit();
				return true;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}