<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="accountPackage.Account"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String Id = request.getParameter("user_Id");
	String Pw = request.getParameter("user_Pw");
	String Fname = request.getParameter("user_Fname");
	String Lname = request.getParameter("user_Lname");
	String Phonenumber = request.getParameter("user_Phone");
	String Email = request.getParameter("user_Email");
	String Birthdate = request.getParameter("user_Birthdate");
	String Sex = request.getParameter("user_Sex");
	String Job = request.getParameter("user_Job");
	String Address = request.getParameter("user_Address");
	String Zipcode = request.getParameter("user_Zipcode");

	
	if (Id.equals("") || Pw.equals("") || Fname.equals("") || Lname.equals("") || Phonenumber.equals("")|| Email.equals("")) {
		out.println("<script>");
		out.println("alert('필수 입력사항이 누락되었습니다.');");
		out.println("history.back();");
		out.println("</script>");
	}

	else {
		if (Account.register(Id,Pw,Fname,Lname,Phonenumber,Email,Birthdate,Sex,Job,Address,Zipcode)) {
			out.println("<script>");
			out.println("alert('회원가입 완료!');");
			out.println("location.href='../view/main.jsp';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('회원가입이 이루어지지 않았습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
%>
</body>
</html>