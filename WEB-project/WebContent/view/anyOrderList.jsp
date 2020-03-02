<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="domain.order.Order"%>
<%@ page import="buyerPackage.Buyer"%>
<%@ page import="accountPackage.Account"%>

<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/dataTables.bootstrap.min.css">
<title></title>
</head>
<body>

	<jsp:include page="top.jsp" flush="false"/>

	<%
		//로긴한사람이라면	 userID라는 변수에 해당 아이디가 담기고 그렇지 않으면 null값
		String Id = null;
		String Lname = null;
		String accountNum = null;
		String type = null;
		if (session.getAttribute("user_Id")!=null) {
			Id=((String)session.getAttribute("user_Id"));
			type = (String) session.getAttribute("user_Type");
			Lname = (String) session.getAttribute("user_Lname");
			accountNum = (String) session.getAttribute("user_Num");
			if (type.equals("Seller")) {
				out.println("<script>");
				out.println("alert('일반 사용자만 접근할 수 있습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
		}
		else {
			out.println("<script>");
			out.println("alert('로그인이 필요합니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		
		int pageNumber = 1; //기본 페이지 넘버

		//페이지넘버값이 있을때
		if (request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
	%>
	
		<!-- 게시판 -->

	<div class="container">

		<div class="row">
			<table class="table table-striped" id="orderList"
				style="text-align: center; border: 1px solid #dddddd">
				
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">거래 번호</th>
						<th style="background-color: #eeeeee; text-align: center;">거래 일자</th>
						<th style="background-color: #eeeeee; text-align: center;">판매자</th>
						<th style="background-color: #eeeeee; text-align: center;">구매자</th>
						<th style="background-color: #eeeeee; text-align: center;">상세 내용</th>
					</tr>
				</thead>
				<tbody>
					<%

					ArrayList<Order> list = Buyer.getOrder(Integer.parseInt(accountNum));
						for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<td><%=list.get(i).getOrderNum()%></td>
						<td><%=list.get(i).getOrderDate()%></td>
						<td><%=list.get(i).getBuyer() == 0 ? "Unknown" : list.get(i).getBuyer()%></td>
						<td><%=list.get(i).getSeller() == 0 ? "Unknown" : list.get(i).getSeller()%></td>
						<td><a href="orderInfo.jsp?regNum=<%=list.get(i).getRegNum()%>">'<%=list.get(i).getRegNum()%>' 매물 정보 조회</a></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>	
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery.dataTables.min.js"></script>
	<script src="../js/dataTables.bootstrap.min.js"></script>
	<script>
	$('#orderList').dataTable();
	</script>
	
</body>
</html>