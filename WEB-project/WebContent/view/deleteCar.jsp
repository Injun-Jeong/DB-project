<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>

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
				<form method="post" action="../func/deleteCarAction.jsp">
					<h3 style="text-align: center;">매물 삭제</h3>
					
					<div class="form-group">
						삭제할 매물 번호를 입력하세요: <input type="text" class="form-control"
							placeholder="매물 번호" name="regNum" maxlength="13">
					</div>
					
					<input type="submit" class="btn btn-primary form-control"
						value="매물 삭제 하기">
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>