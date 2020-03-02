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
	String Id=null;
	if(session.getAttribute("user_Id")!=null)
		Id=(String)session.getAttribute("user_Id");
	if(Id!=null){
		out.println("<script>");
		out.println("alert('이미 로그인이 되어있습니다.');");
		out.println("location.href='../view/main.jsp'");
		out.println("</script>");
	}
	Id=request.getParameter("user_Id");
	String Pw=request.getParameter("user_Pw");	
	if(Id.equals("") || Pw.equals("")){
		out.println("<script>");
		out.println("alert('필수 입력사항이 누락되었습니다.');");
		out.println("history.back();");
		out.println("</script>");
	}
	else{
		if(Account.login(Id,Pw)){
			session.setAttribute("user_Id", Id);
			session.setAttribute("user_Lname",Account.get_Accountname());
			session.setAttribute("user_Type",Account.get_loginAccounttype());
			session.setAttribute("user_Num", String.valueOf(Account.get_loginAccount()));
			out.println("<script>");
			out.println("alert('"+Account.get_Accountname()+"님, 환영합니다.');");
			out.println("location.href='../view/main.jsp';");
			out.println("</script>");

		}
		else{
			out.println("<script>");
			out.println("alert('로그인 정보가 잘못되었습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
%>
</body>
</html>