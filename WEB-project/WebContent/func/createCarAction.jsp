<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="sellerPackage.Seller"%>
<%@ page import="accountPackage.Account"%>
<%@ page import="java.util.*"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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

	request.setCharacterEncoding("UTF-8");

	int Transmission = Integer.parseInt(request.getParameter("transmission"));
	String ReleaseDate = request.getParameter("releaseDate");
	int Vin = Integer.parseInt(request.getParameter("vin"));
	int Condition = Integer.parseInt(request.getParameter("condition"));
	int Displacement = Integer.parseInt(request.getParameter("displacement"));
	int Price = Integer.parseInt(request.getParameter("price"));
	int DetailedModel = Integer.parseInt(request.getParameter("DetailedModel_List"));
	
	int Registerer = Integer.parseInt(accountNum);
	
	int Category = Integer.parseInt(request.getParameter("category"));
	int Mpg = Integer.parseInt(request.getParameter("mpg"));
	int Mileage = Integer.parseInt(request.getParameter("mileage"));
	
	ArrayList<Integer> Fuels = new ArrayList<Integer>();
	Fuels.add(Integer.parseInt(request.getParameter("fuel_1")));
	if (!request.getParameter("fuel_2").equals("0")) {
		Fuels.add(Integer.parseInt(request.getParameter("fuel_2")));
	}

	ArrayList<Integer> Colors = new ArrayList<Integer>();
	Colors.add(Integer.parseInt(request.getParameter("color_1")));
	if (!request.getParameter("color_2").equals("0")) {
		Colors.add(Integer.parseInt(request.getParameter("color_2")));
	}

	if (Seller.createNewCar(Transmission, ReleaseDate, Vin, Condition, Displacement, Price, DetailedModel, Registerer, Category, Mpg, Mileage, Fuels, Colors)) {
		out.println("<script>");
		out.println("alert('새로운 매물 등록 완료!');");
		out.println("location.href='../view/main.jsp';");
		out.println("</script>");
	} else {
		out.println("<script>");
		out.println("alert('다시 시도해주십시오.');");
		out.println("history.back();");
		out.println("</script>");
	}
%>

</body>
</html>