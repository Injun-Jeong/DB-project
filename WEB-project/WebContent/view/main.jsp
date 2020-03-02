
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<title>My Homepage</title>
</head>
<body>
	<%
		String Id = null;
		String Lname=null;
		if (session.getAttribute("user_Id") != null){
			Id = (String) session.getAttribute("user_Id");
			Lname=(String)session.getAttribute("user_Lname");
		}
	%>
	<jsp:include page="top.jsp" flush="false"/>
	<div class="container">
		<div class="jumbotron">
			<h1>Used Car Sales</h1>
			<p>환영합니다<%if(Id != null){out.println(", " + Lname + "님");}%>!</p>
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
					<img src="../image/1.jpg" alt="Los Angeles">
				</div>
				<%
					for(int i=2;i<10;i++){
						out.println("<div class=\"item\">");
						out.println("<img src=\"../image/"+i+".jpg\">");
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
	<script src="../js/bootstrap.js"></script>
</body>
</html>