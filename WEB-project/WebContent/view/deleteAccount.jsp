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
				<form method="post" action="../func/deleteAccountAction.jsp">
					<h3 style="text-align: center;">사용자 계정 삭제</h3>
					
					<b>사용자 계정 삭제를 원할 시, 아래의 문장을 정확히 작성하여 주세요.</b><br>
					<div class="form-group">
						I want to delete my account <input type="text" class="form-control"
							placeholder="Input here" name="str" maxlength="28">
					</div>
					
					<input type="submit" class="btn btn-primary form-control"
						value="계정 삭제 하기">
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>