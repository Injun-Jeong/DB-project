
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
				<li><a href="main.jsp">메인</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="login.jsp">로그인</a></li>
				<li><a href="join.jsp">회원가입</a></li>
			</ul>
		</div>
	</nav>
	<!-- 로긴폼 -->

	<div class="container">

		<div class="col-lg-4"></div>

		<div class="col-lg-4">

			<div class="jumbotron" style="padding-top: 20px;">
				<form method="post" action="../func/signUpAction.jsp">
					<h3 style="text-align: center;">회원가입화면</h3>
					* 필수 입력사항
					<div class="form-group">
						* Id: <input type="text" class="form-control" placeholder="아이디"
							name="user_Id" maxlength="20">
					</div>
					<div class="form-group">
						* Pw: <input type="password" class="form-control"
							placeholder="비밀번호" name="user_Pw" maxlength="20">
					</div>
					<div class="form-group">
						* First name: <input type="text" class="form-control"
							placeholder="성" name="user_Fname" maxlength="20">
					</div>
					<div class="form-group">
						* Last name: <input type="text" class="form-control"
							placeholder="이름" name="user_Lname" maxlength="20">
					</div>
					<div class="form-group">
						* Phone number: <input type="text" class="form-control"
							placeholder="010-XXXX-XXXX" name="user_Phone" maxlength="13">
					</div>
					<div class="form-group">
						* Email address: <input type="text" class="form-control"
							placeholder="something@example.com" name="user_Email"
							maxlength="50">
					</div>
					<div class="form-group">
						Birth date: <input type="text" class="form-control"
							placeholder="1XXX-XX-XX" name="user_Birthdate" maxlength="10">
					</div>
					<div class="form-group">
						Sex: <select class="form-control" name="user_Sex">
							<option></option>
							<option>M</option>
							<option>F</option>
						</select>
					</div>
					<div class="form-group">
						Job: <input type="text" class="form-control" placeholder="직업"
							name="user_Job" maxlength="100">
					</div>
					<div class="form-group">
						Address: <input type="text" class="form-control" placeholder="주소"
							name="user_Address" maxlength="100">
					</div>
					<div class="form-group">
						Zipcode: <input type="text" class="form-control"
							placeholder="우편번호 5자리" name="user_Zipcode" maxlength="5">
					</div>
					<input type="submit" class="btn btn-primary form-control"
						value="회원가입">
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>