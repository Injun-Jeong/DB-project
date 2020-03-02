
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>My Homepage</title>
</head>
<body>
	<%
		String Id = null;
		if (session.getAttribute("user_Id") != null)
			Id = (String) session.getAttribute("user_Id");
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
				<li><a href="logOut.jsp">로그아웃</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="jumbotron">
			<h1>Used Car Sales</h1>
			<p>Welcome To Your Website</p>
		</div>
	</div>
	<div class="container">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<%
					for(int i=1;i<9;i++)
						out.println("<li data-target=\"#myCarousel\" data-slide-to=\""+i+"\"></li>");
				%>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="image/1.jpg" alt="Los Angeles">
				</div>
				<%
					for(int i=2;i<10;i++){
						out.println("<div class=\"item\">");
						out.println("<img src=\"image/"+i+".jpg\">");
						out.println("</div>");
					}
				%>
			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>