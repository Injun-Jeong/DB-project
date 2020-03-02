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
				<li class="active"><a href="main.jsp">����</a></li>
				<%
				if(Id!=null){
					if(type.equals("Buyer")){
						%>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">�� ���� <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="accountInfo.jsp">��ȸ/����</a></li>
						<li><a href="anyOrderList.jsp">�ֹ� ���� ��ȸ</a></li>
						<li><a href="deleteAccount.jsp">ȸ�� Ż��</a></li>
					</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">�Ź��˻� <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="simpleSearchWindow.jsp">���� �˻�</a></li>
						<li><a href="advancedSearchWindow.jsp">���� �˻�</a></li>
					</ul></li>
				
				<%
					} else if (type.equals("Seller")) {
				%>
						<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">�Ź� ���� <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="createCar.jsp">���ο� �Ź� ���</a></li>
						<li><a href="updateCar.jsp">�Ź� ���� ����</a></li>
						<li><a href="modifyCarShowing.jsp">�Ź� ����/����� ����</a></li>
						<li><a href="deleteCar.jsp">�Ź� ����</a></li>
						
					</ul></li>
					
						<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">�Ź��˻� <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="simpleSearchWindow.jsp">���� �˻�</a></li>
						<li><a href="advancedSearchWindow.jsp">���� �˻�</a></li>
					</ul></li>
						
						<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">�ŷ� ���� ���� <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="allOrderList.jsp">��ü ���� ��ȸ</a></li>
						<li><a href="orderStats.jsp">�ŷ� ���� �м�</a></li>
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
				<li><a href="login.jsp">�α���</a></li>
				<li><a href="signUp.jsp">ȸ������</a></li>
				<%
					} else {
				%>
				<li><a href="../func/logOut.jsp">�α׾ƿ�</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</nav>
	

</body>
</html>