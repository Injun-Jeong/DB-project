<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="sellerPackage.Seller"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
</head>
<body>

<%
	request.setCharacterEncoding("UTF-8");
	String regNum = request.getParameter("regNum");
	
	if (Seller.changeShowing(regNum)) {
		out.println("<script>");
		out.println("alert('공개/비공개 수정 완료!');");
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