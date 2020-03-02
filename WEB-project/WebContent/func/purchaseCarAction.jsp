<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="buyerPackage.Buyer"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");	
		String Id = null;
		String Lname = null;
		String accountNum = null;
		String type = null;
		int reg_num;
		if (session.getAttribute("user_Id") != null) {
			Id = ((String) session.getAttribute("user_Id"));
			type = (String) session.getAttribute("user_Type");
			Lname = (String) session.getAttribute("user_Lname");
			accountNum = (String) session.getAttribute("user_Num");

		}
		reg_num=Integer.parseInt(request.getParameter("regNum"));
		
		if(Buyer.purchase(reg_num,Integer.parseInt(accountNum))){
			out.println("<script>");
			out.println("alert('구매에 성공하였습니다.')");
			out.println("location.href='../view/main.jsp'");
			out.println("</script>");
		}
		else{
			out.println("<script>");
			out.println("alert('구매에 실패하였습니다..')");
			out.println("history.back()");
			out.println("</script>");
		}
	%>
</body>
</html>