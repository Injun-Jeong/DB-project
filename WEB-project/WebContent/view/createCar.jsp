<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="util.DatabaseConnection"%>
<%@ page import="java.sql.*"%>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<title></title>
</head>
<body>

	<jsp:include page="top.jsp" flush="false"/>

	<div class="container">

		<div class="col-lg-4"></div>

		<div class="col-lg-4">

			<div class="jumbotron" style="padding-top: 20px;">
				<form method="post" action="../func/createCarAction.jsp">
					<h3 style="text-align: center;">새로운 매물 등록</h3>
					
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
    							<option value="1">Auto</option>
    							<option value="2">Manual</option>
    							<option value="3">Semi-Auto</option>
    							<option value="4">CVT</option>
  							</select>
					</div>
					<div class="form-group">
					<label>Release Date</label>
						 <input type="text" class="form-control"
							placeholder="MM/YYYY" name="releaseDate" maxlength="13">
					</div>
					
					<div class="form-group">
					<label>VIN</label>
						 <input type="text" class="form-control"
							placeholder="------ (six digits)" name="vin" maxlength="13">
					</div>
					
					<div class="form-group">
					<label>Condition</label> 
  							<select class="form-control" class="form-control" name="condition">
  								<option value="0">---------</option>
    							<option value="1">Excellent</option>
    							<option value="2">Good</option>
    							<option value="3">Fair</option>
    							<option value="4">Poor</option>
  							</select>
					</div>
					
					<div class="form-group">
					<label>Displacement</label> 
  							<select class="form-control" class="form-control" name="displacement">
  								<option value="0">---------</option>
    							<option value="1">less than 500</option>
    							<option value="2">less than 1,000</option>
    							<option value="3">less than 1,500</option>
    							<option value="4">less than 2,000</option>
    							<option value="5">less than 2,500</option>
    							<option value="6">less than 3,000</option>
    							<option value="7">less than 3,500</option>
    							<option value="8">less than 4,000</option>
    							<option value="9">more than 4,000</option>
  							</select>
					</div>
					
					<div class="form-group">
					<label>Price</label>
						<input type="text" class="form-control"
							placeholder="unit is 10,000 WON." name="price" maxlength="13">
					</div>
					
					<div class="form-group">
					<label>Category</label> 
  							<select class="form-control" class="form-control" name="category">
  								<option value="0">---------</option>
    							<option value="1">Small</option>
    							<option value="2">Semi-Medium</option>
    							<option value="3">Medium</option>
    							<option value="4">Semi-Large</option>
    							<option value="5">Large</option>
    							<option value="6">Sport Car</option>
    							<option value="7">SUV</option>
    							<option value="8">Etc</option>
  							</select>
					</div>
					
					<div class="form-group">
					<label>MPG</label>
						<input type="text" class="form-control"
							placeholder="km/L" name="mpg" maxlength="13">
					</div>
					<div class="form-group">
					<label>Mileage</label>
						<input type="text" class="form-control"
							placeholder="km" name="mileage" maxlength="13">
					</div>
					
					
					<div class="form-group">
					<label>Fuel</label>
					<br />
						<b>If your car is hybrid, then please input the SECOND FUEL data.</b><br>
  							<select name="fuel_1">
  								<option value="0">First Fuel</option>
  								<option value="0">----------------------</option>
    							<option value="1">Gasoline</option>
    							<option value="2">Diesel</option>
    							<option value="3">LPG</option>
    							<option value="4">Electronic</option>
    							<option value="5">CNG</option>
    							<option value="6">Hydrogen</option>
  							</select>
  							
  							<select name="fuel_2">
  								<option value="0">Second Fuel</option>
  								<option value="0">----------------------</option>
    							<option value="1">Gasoline</option>
    							<option value="2">Diesel</option>
    							<option value="3">LPG</option>
    							<option value="4">Electronic</option>
    							<option value="5">CNG</option>
    							<option value="6">Hydrogen</option>
  							</select>
  					</div>
					
					<div class="form-group">
					<label>Color</label>
					<br />
						<b>If your car has more than one color, then please input the SECOND COLOR data.</b><br>
  							<select name="color_1">
  								<option value="0">First Color</option>
  								<option value="0">----------------------</option>
    							<option value="1">White</option>
    							<option value="2">Pearl</option>
    							<option value="3">Black</option>
    							<option value="4">Blue</option>
    							<option value="5">Dark Gray</option>
    							<option value="6">Silver</option>
    							<option value="7">Silver Gray</option>
    							<option value="8">Skyblue</option>
    							<option value="9">Light Green</option>
    							<option value="10">Blue Green</option>
    							<option value="11">Galactic</option>
    							<option value="12">Light Gray</option>
    							<option value="13">Red</option>
    							<option value="14">Orange</option>
    							<option value="15">Wine</option>
    							<option value="16">Purple</option>
    							<option value="17">Pink</option>
    							<option value="18">Yellow</option>
    							<option value="19">Reed</option>
    							<option value="20">Light Gold</option>
    							<option value="21">Brown</option>
    							<option value="22">Green</option>
    							<option value="23">Gold</option>
    							<option value="24">Yello Green</option>
    							<option value="25">Etc</option>
  							</select>
  							
  							<select name="color_2">
  								<option value="0">Second Color</option>
  								<option value="0">----------------------</option>
    							<option value="1">White</option>
    							<option value="2">Pearl</option>
    							<option value="3">Black</option>
    							<option value="4">Blue</option>
    							<option value="5">Dark Gray</option>
    							<option value="6">Silver</option>
    							<option value="7">Silver Gray</option>
    							<option value="8">Skyblue</option>
    							<option value="9">Light Green</option>
    							<option value="10">Blue Green</option>
    							<option value="11">Galactic</option>
    							<option value="12">Light Gray</option>
    							<option value="13">Red</option>
    							<option value="14">Orange</option>
    							<option value="15">Wine</option>
    							<option value="16">Purple</option>
    							<option value="17">Pink</option>
    							<option value="18">Yellow</option>
    							<option value="19">Reed</option>
    							<option value="20">Light Gold</option>
    							<option value="21">Brown</option>
    							<option value="22">Green</option>
    							<option value="23">Gold</option>
    							<option value="24">Yello Green</option>
    							<option value="25">Etc</option>
  							</select>
  					</div>
					
					<input type="submit" class="btn btn-primary form-control"
						value="새로운 매물 등록하기">
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
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
			var url="../func/detailedModelAction_v2.jsp"
			url+="?Model_name="+str;
			xmlHttp.onreadystatechange=detailedModelChange;
			xmlHttp.open("GET",url,true);
			xmlHttp.send(null);
		}

</script>
</body>
</html>