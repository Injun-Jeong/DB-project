<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="domain.vehicle.Vehicle"%>
<%@ page import="domain.vehicle.VehicleInfo"%>
<%@ page import="accountPackage.Account"%>

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

		}
	%>
	<jsp:include page="top.jsp" flush="false" />
	<%
		Vehicle vehicle = VehicleInfo.getVehicleInfo(Integer.parseInt(request.getParameter("regNum")));
	%>
	<div class="container">
		<div class="row">
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">

				<div class="col-lg-4"></div>

				<div class="col-lg-4">

					<div class="jumbotron" style="padding-top: 20px;">

						<h3 style="text-align: center;">거래 매물 상세 정보</h3>

						<div class="form-group">
							<b>매물 번호</b>:
							<%=vehicle.getRegNum()%>
						</div>

						<div class="form-group">
							<b>변속기</b>:
							<%=vehicle.getTransmission()%>
						</div>

						<div class="form-group">
							<b>출고 일자</b>:
							<%=vehicle.getReleaseDate()%>
						</div>

						<div class="form-group">
							<b>차량 번호</b>:
							<%=vehicle.getVin()%>
						</div>

						<div class="form-group">
							<b>차량 상태</b>:
							<%=vehicle.getCondition()%>
						</div>

						<div class="form-group">
							<b>엔진 유형</b>:
							<%=vehicle.getDisplacement()%>
						</div>

						<div class="form-group">
							<b>가격(단위: 10,000 원)</b>:
							<%=vehicle.getPrice()%>
						</div>

						<div class="form-group">
							<b>세부 모델명</b>:
							<%=vehicle.getDetailedModel()%>
						</div>

						<div class="form-group">
							<b>판매자 번호</b>:
							<%=vehicle.getRegisterer()%>
						</div>

						<div class="form-group">
							<b>차량 종류</b>:
							<%=vehicle.getCategory()%>
						</div>

						<div class="form-group">
							<b>하이브리드 여부</b>:
							<%=vehicle.getIs_hybrid()%>
						</div>

						<div class="form-group">
							<b>연비</b>:
							<%=vehicle.getMpg()%>
						</div>

						<div class="form-group">
							<b>주행거리</b>:
							<%=vehicle.getMileage()%>
						</div>
						<%
						if(type.equals("Buyer") && !vehicle.getIs_sold())
							out.println("<a href=\"../func/purchaseCarAction.jsp?regNum="+vehicle.getRegNum()+"\" class=\"btn btn-info\" role=\"button\">구매하기</a>");
						%>

					</div>
				</div>
		</div>
</body>
</html>