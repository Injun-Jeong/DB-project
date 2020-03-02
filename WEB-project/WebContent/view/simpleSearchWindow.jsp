
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vehiclePackage.Vehicle"%>
<%@ page import="util.DatabaseConnection"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<title>Search View</title>
</head>
<body>
	<%
		String Id = null;
		String Lname=null;
		if (session.getAttribute("user_Id") != null){
			Id = (String) session.getAttribute("user_Id");
			Lname=(String)session.getAttribute("user_Lname");
		}
		
		ArrayList<String> MakeList= new ArrayList<>();
		ArrayList<String> ModelList= new ArrayList<>();
		ArrayList<String> DetailList= new ArrayList<>();
		
		MakeList = Vehicle.getMakeList(DatabaseConnection.getConnection());
	%>
	
	<jsp:include page="top.jsp" flush="false"/>
	
	<!-- search form -->
	
	<div class="container">

		<div class="col-lg-4"></div>

		<div class="col-lg-4">

			<div class="jumbotron" style="padding-top: 20px;">
				<form method="post" action="searchView.jsp">
					<h3 style="text-align: center;">매물 찾기</h3>
					* 제조사 별 조회<div class="form-group">
						Make: <select class="form-control" name="Make_list">
						<option></option>
						<%
							for(String Make: MakeList)
								out.println("<option>" + Make +"</option>");
						%>
						</select>
					</div>
					<input type="submit" class="btn btn-primary form-control"
						value="조회">
				</form>
				
				<form method="post" action="searchView.jsp">
					* 이름 별 조회<div class="form-group">
						Name: <input type="text" class="form-control" name="Name" maxlength="20">
					</div>
					<input type="submit" class="btn btn-primary form-control"
						value="검색">
				</form>
				
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
	
</body>
</html>