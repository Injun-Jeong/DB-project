<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
</head>
<body>
	<%
		String Id = null;
		String type = null;
		if (session.getAttribute("user_Id") != null) {
			Id = (String) session.getAttribute("user_Id");
			type = (String) session.getAttribute("user_Type");
		}
	%>
	
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expaned="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">Used Car Sales</a>
		</div>
		<div class="collapse navbar-collapse"
			id="#bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav">
				<li class="active"><a href="main.jsp">메인</a></li>
				<%
				if(Id!=null){
					if(type.equals("Buyer")){
						%>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">내 정보 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="accountInfo.jsp">조회/변경</a></li>
						<li><a href="anyOrderList.jsp">주문 내역 조회</a></li>
						<li><a href="deleteAccount.jsp">회원 탈퇴</a></li>
					</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">매물검색 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="simpleSearchWindow.jsp">간단 검색</a></li>
						<li><a href="advancedSearchWindow.jsp">세부 검색</a></li>
					</ul></li>
				
				<%
					} else if (type.equals("Seller")) {
				%>
						<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">매물 관리 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="createCar.jsp">새로운 매물 등록</a></li>
						<li><a href="updateCar.jsp">매물 정보 변경</a></li>
						<li><a href="modifyCarShowing.jsp">매물 공개/비공개 설정</a></li>
						<li><a href="deleteCar.jsp">매물 삭제</a></li>
						
					</ul></li>
					
						<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">매물검색 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="simpleSearchWindow.jsp">간단 검색</a></li>
						<li><a href="advancedSearchWindow.jsp">세부 검색</a></li>
					</ul></li>
						
						<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">거래 내역 관리 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="allOrderList.jsp">전체 내역 조회</a></li>
						<li><a href="orderStats.jsp">거래 내역 분석</a></li>
					</ul></li>
						<%
					}
				}
				%>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<%
					if (Id == null) {
				%>
				<li><a href="login.jsp">로그인</a></li>
				<li><a href="signUp.jsp">회원가입</a></li>
				<%
					} else {
				%>
				<li><a href="../func/logOut.jsp">로그아웃</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</nav>
	

</body>
</html>