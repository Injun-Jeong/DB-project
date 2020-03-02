<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vehiclePackage.Vehicle"%>
<%@ page import="util.DatabaseConnection"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	String Model_name=request.getParameter("Model_name");
	String buffer="<select class=\"form-control\" name=\"DetailedModel\" id=\"DetailedModel\"> <option value=\"0\">Select DetailedModel</option>";
				String sql="SELECT DETAIL_NAME FROM MAKE, MODEL,DETAILED_MODEL WHERE MANUFACTURER=MAKE_NUM AND PARENT_MODEL=MODEL_NUM AND MODEL_NAME=?";
				Connection conn=DatabaseConnection.getConnection();
				PreparedStatement ps =conn.prepareStatement(sql);
				ps.setString(1,Model_name);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
				buffer+="<option value=\""+rs.getString(1)+"\">"+rs.getString(1)+"</option>";
					}
				
				buffer+="</select>";
				response.getWriter().println(buffer);

			%>
</body>
</html>