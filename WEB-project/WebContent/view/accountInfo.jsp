<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="accountPackage.Account"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="util.DatabaseConnection"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<title></title>
</head>
<body>
	<%
		String Id = null;
		String Lname = null;
		String accountNum = null;
		String type = null;
		if (session.getAttribute("user_Id")!=null) {
			Id=((String)session.getAttribute("user_Id"));
			type = (String) session.getAttribute("user_Type");
			Lname = (String) session.getAttribute("user_Lname");
			accountNum = (String) session.getAttribute("user_Num");
			if (type.equals("Seller")) {
				out.println("<script>");
				out.println("alert('일반 사용자만 접근할 수 있습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
		}
		else {
			out.println("<script>");
			out.println("alert('로그인이 필요합니다.');");
			out.println("history.back();");
			out.println("</script>");
		}

		// 회원정보 가져오는 부분인 부분 
		String Birthdate = null;
		String Pw = null;
		String Phone = null;
		String Email = null;
		String Sex = null;
		String Job = null;
		String Fname = null;
		String Zipcode = null;
		String Address = null;
		
		try {
		String sql = "SELECT * FROM ACCOUNT WHERE Id = ?";
		Connection conn= DatabaseConnection.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, Id);
		ResultSet result = ps.executeQuery();
		if (result.next()) {
			if(result.getDate(2)!=null)
				Birthdate = result.getDate(2).toString();
			Pw = result.getString(4);
			Phone = result.getString(5);
			Email = result.getString(6);
			if(result.getString(7)!=null)
				Sex = result.getString(7);
			if(result.getString(8)!=null)
				Job = result.getString(8);
			Fname = result.getString(10);
			if(result.getString(12)!=null)
				Zipcode = result.getString(12);
			if(result.getString(13)!=null)
				Address = result.getString(13);
		}

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	%>
	<jsp:include page="top.jsp" flush="false" />
	<!-- 로긴폼 -->

	<div class="container">

		<div class="col-lg-4"></div>

		<div class="col-lg-4">

			<div class="jumbotron" style="padding-top: 20px;">
				<form method="post" action="../func/accountInfoAction.jsp">
					<h3 style="text-align: center;">내 정보</h3>
					<div class="form-group">
						Id:
						<% out.println(Id);%>
					</div>
					<div class="form-group">
						* Pw: <input type="password" class="form-control"
							placeholder="" name="user_Pw" maxlength="20">
					</div>
					<div class="form-group">
						First name:
						 <% out.println(Fname);%> 
					</div>
					<div class="form-group">
						Last name:
						 <% out.println(Lname);%> 
					</div>
					<div class="form-group">
						Phone number: <input type="text" class="form-control"
							placeholder="<%=Phone %>" name="user_Phone" maxlength="13">
					</div>
					<div class="form-group">
						Email address: <input type="text" class="form-control"
							placeholder="<%=Email %>" name="user_Email"
							maxlength="50">
					</div>
					<div class="form-group">
						Birth date:
						 <%out.println(Birthdate);%> 
					</div>
					<div class="form-group">
						Sex: 
						 <% out.println(Sex);%> 
					</div>
					<div class="form-group">
						Job: <input type="text" class="form-control" placeholder="<%=Job %>"
							name="user_Job" maxlength="100">
					</div>
					<div class="form-group">
						Address: <input type="text" class="form-control" placeholder="<%=Address %>"
							name="user_Address" maxlength="100">
					</div>
					<div class="form-group">
						Zipcode: <input type="text" class="form-control"
							placeholder="<%=Zipcode %>" name="user_Zipcode" maxlength="5">
					</div>
					
					<input type="submit" class="btn btn-primary form-control"
						value="수정">
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>