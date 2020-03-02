<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="accountPackage.Account"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
</head>
<body>

<%
	request.setCharacterEncoding("UTF-8");
	String accountNum = Integer.toString(Account.get_loginAccount());
	
	if (request.getParameter("str").equals("I want to delete my account") && Account.deleteAccount(accountNum)) {
		out.println("<script>");
		out.println("alert('계정 삭제가 완료 되었습니다.');");
		out.println("</script>");
		session.invalidate();	
		out.println("<script>");
		out.println("location.href='../view/main.jsp';");
		out.println("</script>");
	} else {
		out.println("<script>");
		out.println("alert('다시 시도하세요.');");
		out.println("history.back();");
		out.println("</script>");
	}
%>

</body>
</html>