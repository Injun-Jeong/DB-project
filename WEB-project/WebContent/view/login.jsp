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
		if (session.getAttribute("user_Id") != null)
			Id = (String) session.getAttribute("user_Id");
	%>
 <nav class="navbar navbar-default">
  <div class="navbar-header">
   <button type="button" class="navbar-toggle collapsed" 
    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
    aria-expaned="false">
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
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
 </nav>
 <!-- 로긴폼 -->

 <div class="container">

  <div class="col-lg-4"></div>

  <div class="col-lg-4">

   <div class="jumbotron" style="padding-top: 20px;">
   <form method="post" action="../func/loginAction.jsp">
    <h3 style="text-align: center;"> 로그인화면 </h3>
    <div class="form-group">
     <input type="text" class="form-control" placeholder="아이디" name="user_Id" maxlength="20">
    </div>
    <div class="form-group">
     <input type="password" class="form-control" placeholder="비밀번호" name="user_Pw" maxlength="20">
    </div>
    <input type="submit" class="btn btn-primary form-control" value="로그인">
   </form>
  </div>
 </div>
</div>
 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
 <script src="../js/bootstrap.js"></script>
</body>
</html>