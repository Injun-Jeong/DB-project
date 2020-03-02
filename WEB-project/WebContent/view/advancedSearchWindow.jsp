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
					<div class="form-group">
					<label>Make</label>
					<select class="form-control Make selectFilter" name="Make_List" id="Make_List" onchange="showModel(this.value)">
						<option>Make_name</option>
						<%
							String sql="SELECT MAKE_NAME FROM MAKE";
							Connection conn=DatabaseConnection.getConnection();
							PreparedStatement ps =conn.prepareStatement(sql);
							ResultSet rs=ps.executeQuery();
							
							while(rs.next()){
								%>
							<option value="<%=rs.getString(1)%>"><%=rs.getString(1)%></option>
							<%
								}
							%>
							 </select>
							 
					<label>Model</label>	
						<select class="form-control" name="Model_List" id="Model_List" onchange="showDetailedModel(this.value)">
						<option>Select Model_name</option>
						</select>
					<label>DetailedModel</label>	
						<select class="form-control" name="DetailedModel_List" id="DetailedModel_List">
						<option>Select DetailedModel_name</option>
						</select> 
						</div>
						<div class="form-group">
							<label>Transmission</label>	
  							<select class="form-control" name="transmission">
  								<option value="0">Select Transmission</option>
  								<option value="0">---------</option>
    							<option value="auto">Auto</option>
    							<option value="manual">Manual</option>
    							<option value="semi">Semi-Auto</option>
    							<option value="cvt">CVT</option>
  							</select>
					</div>
							
					<div class="form-group">
						<label>Condition</label>	
  							<select class="form-control" name="condition">
  								<option value="0">Select Condition</option>
  								<option value="0">---------</option>
    							<option value="excellent">Excellent</option>
    							<option value="good">Good</option>
    							<option value="fair">Fair</option>
    							<option value="poor">Poor</option>
  							</select>
					</div>
					
					<div class="form-group">
						<label>Displacement</label>	
  							<select class="form-control" name="displacement">
  								<option value="0">Select Displacement</option>
  								<option value="0">---------</option>
    							<option value="500">less than 500</option>
    							<option value="1000">less than 1,000</option>
    							<option value="1500">less than 1,500</option>
    							<option value="2000">less than 2,000</option>
    							<option value="2500">less than 2,500</option>
    							<option value="3000">less than 3,000</option>
    							<option value="3500">less than 3,500</option>
    							<option value="4000">less than 4,000</option>
    							<option value="9999">more than 4,000</option>
  							</select>
					</div>
					
					<div class="form-group">
						<label>Category</label>	
  							<select class="form-control" name="category">
  								<option value="0">Select Category</option>
  								<option value="0">---------</option>
    							<option value="Small">Small</option>
    							<option value="Semi-medium">Semi-Medium</option>
    							<option value="Medium">Medium</option>
    							<option value="Semi-large">Semi-Large</option>
    							<option value="Large">Large</option>
    							<option value="Sport Car">Sport Car</option>
    							<option value="SUV">SUV</option>
    							<option value="Etc">Etc</option>
  							</select>
					</div>

					<div class="form-group">
					<label>Fuel</label>	
						<b>If you want hybrid car, then please input the SECOND FUEL data.</b><br>
						
  							<select name="fuel_1">
  								<option value="0">First Fuel</option>
  								<option value="0">----------------------</option>
    							<option value="gasoline">Gasoline</option>
    							<option value="diesel">Diesel</option>
    							<option value="lpg">LPG</option>
    							<option value="electronic">Electronic</option>
    							<option value="cng">CNG</option>
    							<option value="hydogen">Hydrogen</option>
  							</select>
  							
  							<select name="fuel_2">
  								<option value="0">Second Fuel</option>
  								<option value="0">----------------------</option>
    							<option value="gasoline">Gasoline</option>
    							<option value="diesel">Diesel</option>
    							<option value="lpg">LPG</option>
    							<option value="electronic">Electronic</option>
    							<option value="cng">CNG</option>
    							<option value="hydogen">Hydrogen</option>
  							</select>
  					</div>
					
					<div class="form-group">
					<label>Color</label>	
						<b>If you want to search car having more color than one, then please input the SECOND COLOR data.</b><br>
  							<select name="color_1">
  								<option value="0">First Color</option>
  								<option value="0">----------------------</option>
    							<option value="white">White</option>
    							<option value="pearl">Pearl</option>
    							<option value="black">Black</option>
    							<option value="blue">Blue</option>
    							<option value="dark">Dark Gray</option>
    							<option value="silver">Silver</option>
    							<option value="silver Gray">Silver Gray</option>
    							<option value="skyblue">Skyblue</option>
    							<option value="light Green">Light Green</option>
    							<option value="blue Green">Blue Green</option>
    							<option value="galactic">Galactic</option>
    							<option value="light Gray">Light Gray</option>
    							<option value="red">Red</option>
    							<option value="orange">Orange</option>
    							<option value="wine">Wine</option>
    							<option value="purple">Purple</option>
    							<option value="pink">Pink</option>
    							<option value="yellow">Yellow</option>
    							<option value="reed">Reed</option>
    							<option value="light Gold">Light Gold</option>
    							<option value="brown">Brown</option>
    							<option value="Green">Green</option>
    							<option value="gold">Gold</option>
    							<option value="yello Green">Yello Green</option>
    							<option value="etc">Etc</option>
  							</select>
  							
  							<select name="color_2">
  								<option value="0">Second Color</option>
  								<option value="0">----------------------</option>
    								<option value="White">White</option>
    							<option value="white">White</option>
    							<option value="pearl">Pearl</option>
    							<option value="black">Black</option>
    							<option value="blue">Blue</option>
    							<option value="dark">Dark Gray</option>
    							<option value="silver">Silver</option>
    							<option value="silver Gray">Silver Gray</option>
    							<option value="skyblue">Skyblue</option>
    							<option value="light Green">Light Green</option>
    							<option value="blue Green">Blue Green</option>
    							<option value="galactic">Galactic</option>
    							<option value="light Gray">Light Gray</option>
    							<option value="red">Red</option>
    							<option value="orange">Orange</option>
    							<option value="wine">Wine</option>
    							<option value="purple">Purple</option>
    							<option value="pink">Pink</option>
    							<option value="yellow">Yellow</option>
    							<option value="reed">Reed</option>
    							<option value="light Gold">Light Gold</option>
    							<option value="brown">Brown</option>
    							<option value="Green">Green</option>
    							<option value="gold">Gold</option>
    							<option value="yello Green">Yello Green</option>
    							<option value="etc">Etc</option>
  							</select>
					</div>
					<input type="submit" class="btn btn-primary form-control"
						value="조회">
				</form>
				
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
	<script src="../js/selectFilter.min.js"></script>

</body>
	<script>
	var xmlHttp;
	function modelChange(){
		if(xmlHttp.readyState === 4 || xmlHttp.readyState === "complete"){
			document.getElementById("Model_List").innerHTML=xmlHttp.responseText;
		}
	}
	function showModel(str) {
			if(typeof XMLHttpRequest !== "undefined"){
				xmlHttp=new XMLHttpRequest();
			}
			else if(window.ActiveXObject){
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if (xmlHttp==null){
				alert('Browser does not support XML');
				return;
			}
			var url="../func/modelAction.jsp"
			url+="?Make_name="+str;
			xmlHttp.onreadystatechange=modelChange;
			xmlHttp.open("GET",url,true);
			xmlHttp.send(null);
		}
	
	var xmlHttp;
	function detailedModelChange(){
		if(xmlHttp.readyState === 4 || xmlHttp.readyState === "complete"){
			document.getElementById("DetailedModel_List").innerHTML=xmlHttp.responseText;
		}
	}
	function showDetailedModel(str) {
			if(typeof XMLHttpRequest !== "undefined"){
				xmlHttp=new XMLHttpRequest();
			}
			else if(window.ActiveXObject){
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if (xmlHttp==null){
				alert('Browser does not support XML');
				return;
			}
			var url="../func/detailedModelAction.jsp"
			url+="?Model_name="+str;
			xmlHttp.onreadystatechange=detailedModelChange;
			xmlHttp.open("GET",url,true);
			xmlHttp.send(null);
		}

	</script>
</html>