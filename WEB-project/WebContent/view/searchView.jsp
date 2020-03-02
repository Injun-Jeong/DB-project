<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vehiclePackage.*"%>
<%@ page import="util.DatabaseConnection"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/dataTables.bootstrap.min.css">
<title></title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		String Name = request.getParameter("Name");
		String Make=request.getParameter("Make_list");
		ArrayList<briefVehicle> vehicleList = new ArrayList<>();
	
		
		if(request.getHeader("referer").equals("http://localhost:8080/WEB-project/view/advancedSearchWindow.jsp")){
			String query="";
			if(!request.getParameter("Make_List").equals("Make_name")){
				query+="Make:"+request.getParameter("Make_List")+"+";
			}
			if(!request.getParameter("Model_List").equals("Select Model_name")&& !request.getParameter("Model_List").equals("0") ){
				query+="Model:"+request.getParameter("Model_List")+"+";
			}
			if(request.getParameter("DetailedModel_list")!=null && !request.getParameter("DetailedModel_list").equals("0")){
				query+="Detail:"+request.getParameter("DetailedModel_list")+"+";
			}
			if(!request.getParameter("transmission").equals("0")){
				query+="Transmission:"+request.getParameter("transmission")+"+";
			}
			if(!request.getParameter("condition").equals("0")){
				query+="Condition:"+request.getParameter("condition")+"+";
			}
			if(!request.getParameter("category").equals("0")){
				query+="Category:"+request.getParameter("category")+"+";
			}
			if(!request.getParameter("displacement").equals("0")){
				query+="Displacement:"+request.getParameter("displacement")+"+";
			}
			if(!request.getParameter("fuel_1").equals("0")){
				query+="Fuel:"+request.getParameter("fuel_1");
				if(!request.getParameter("fuel_2").equals("0")){
					query+="/"+request.getParameter("fuel_2");
				}
				query+="+";
			}
			if(!request.getParameter("color_1").equals("0")){
				query+="Color:"+request.getParameter("color_1");
				if(!request.getParameter("color_2").equals("0")){
					query+="/"+request.getParameter("color_2");
				}
				query+="+";
			}
			if(query.length()==0){
				out.println("<script>");
				out.println("alert('검색된 차량이 존재하지 않습니다')");
				out.println("history.back()");
				out.println("</script>");
			}
			else{
				query=query.substring(0,query.length()-1);
				vehicleList= Vehicle.searchAdvanced(query);
			}
		}
		else{
			if(Name != null)
				vehicleList = Vehicle.searchByName(DatabaseConnection.getConnection(), Name);
			if(Make != null)
				vehicleList = Vehicle.searchByMake(DatabaseConnection.getConnection(), Make);
			
			if(Make ==null && Name==null){
				out.println("<script>");
				out.println("alert('제조사 선택 또는 검색어를 입력해주세요.')");
				out.println("history.back()");
				out.println("</script>");
			}
		}
		
		String Id = null;
		String Lname = null;
		if (session.getAttribute("user_Id") != null) {
			Id = (String) session.getAttribute("user_Id");
			Lname = (String) session.getAttribute("user_Lname");
		}
	%>

	<jsp:include page="top.jsp" flush="false"/>

	<div class="container">
		<h2>검색 결과</h2>
		<p>해당 차량을 클릭하면 매물 정보로 이동합니다.</p>

		<table id="vehicleList" class="table table-hover" style="border: 1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #eeeeee; text-align: center;">Reg#</th>
					<th style="background-color: #eeeeee; text-align: center;">Make</th>
					<th style="background-color: #eeeeee; text-align: center;">Model</th>
					<th style="background-color: #eeeeee; text-align: center;">Detailed Model</th>
					<th style="background-color: #eeeeee; text-align: center;">Category</th>
					<th style="background-color: #eeeeee; text-align: center;">Price</th>
					<th style="background-color: #eeeeee; text-align: center;">Is_sold</th>
					<th style="background-color: #eeeeee; text-align: center;">Release Date</th>
					<th style="background-color: #eeeeee; text-align: center;">Mileage</th>
					<th style="background-color: #eeeeee; text-align: center;">Details</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (briefVehicle b : vehicleList) {
						out.println("<tr class='clickable-row' data-href=#>");
						out.println("<td>" + b.getReg_num() + "</td>");
						out.println("<td>" + b.getMake() + "</td>");
						out.println("<td>" + b.getModel() + "</td>");
						out.println("<td>" + b.getDetail() + "</td>");
						out.println("<td>" + b.getCategory() + "</td>");
						out.println("<td>" + b.getPrice() + "</td>");
						out.println("<td>" + b.getIs_sold() + "</td>");
						out.println("<td>" + b.getRelease_date() + "</td>");
						out.println("<td>" + b.getMileage() + "</td>");
						out.println("<td><a href=\"orderInfo.jsp?regNum="+b.getReg_num()+"\">상세 내역 조회</a></td>");
						out.println("</tr>");
					}
				%>
			</tbody>
		</table>

	</div>

	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery.dataTables.min.js"></script>
	<script src="../js/dataTables.bootstrap.min.js"></script>
	
	<script>
	$('#vehicleList').dataTable();
</script>
</body>
</html>