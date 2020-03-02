<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="sellerPackage.Seller"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
	<%
		
		String Id = null;
		String Lname = null;
		String accountNum = null;
		String type = null;
		if (session.getAttribute("user_Id") != null) {
			Id = ((String) session.getAttribute("user_Id"));
			type = (String) session.getAttribute("user_Type");
			Lname = (String) session.getAttribute("user_Lname");
			accountNum = (String) session.getAttribute("user_Num");
			if (type.equals("Buyer")) {
				out.println("<script>");
				out.println("alert('관리자만 접근할 수 있습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
		} else {
			out.println("<script>");
			out.println("alert('로그인이 필요합니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		TreeMap<String,Integer> yearlyStats=Seller.getYearlyStats();
		TreeMap<String,Integer> makeStats=Seller.getMakeStats();
		
		TreeMap<String,TreeMap<String,Integer>> monthlyStats= new TreeMap<>();
		for(String year : yearlyStats.keySet())
			monthlyStats.put(year,Seller.getMonthlyStats(year));
		ArrayList<String> keyList=new ArrayList<>(yearlyStats.keySet());
	
	%>
	<jsp:include page="top.jsp" flush="false" />
	<form name="FormName">
    		<input type="hidden" name="checkyear" id="checkyear" value="">
		</form>
	<div class="container">
  <div class="row">
    <div class="col-xs-6"><p>연별 통계</p>
      <canvas id="yearlyStats" width="200" height="200"></canvas>
   </div>
   	
     <div class="col-xs-6"><p>월별 통계</p>
      <canvas id="monthlyStats" width="200" height="200"></canvas>
      </div>
    <div class="col-xs-6"><p>제조사별 통계</p>
      <canvas id="makeStats" width="200" height="200"></canvas>
    </div>
  </div>
</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.js"></script>
	<script src="../js/Chart.js"></script>
	<script>
	
	var yearlyChart = document.getElementById('yearlyStats');
	var yearlyBarChart = new Chart(yearlyChart, {
	    type: 'bar',
	    data:{
	    	labels:<%=yearlyStats.keySet()%>,
	    	datasets: [{
	    		label: 'Sum Of Sales',
	    		backgroundColor: "rgba("+Math.floor(Math.random()*255)+ ", " +Math.floor(Math.random()*255)+", "+Math.floor(Math.random()*255) +",1)",
	        	barPercentage: 0.5,
	        	barThickness: 20,
	        	maxBarThickness: 30,
	        	minBarLength: 2,
	        	data:<%=yearlyStats.values()%>
	    	
	    }]},
	    options: {
	        scales: {
	        	 xAxes: [{
	        		   gridLines: {
	                       offsetGridLines: true
	                   }
	             }],
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	
	var monthlyChart = document.getElementById('monthlyStats');
	var monthlyBarChart = new Chart(monthlyChart, {
	    type: 'bar',
	    data:{
	    	labels:['January','February','March','April','May','June','July','August','September','October','November','December'],
	    	<%
	    	if(keyList.size()>0){
	    		%>
	    		datasets: [
	    			{
		    		label: 'Sum Of Sales "<%=keyList.get(0)%>',
		    		backgroundColor: "rgba("+Math.floor(Math.random()*255)+ ", " +Math.floor(Math.random()*255)+", "+Math.floor(Math.random()*255) +",1)",
		    		barPercentage: 0.5,
		    		barThickness: 7,
		    		maxBarThickness: 30,
		    		minBarLength: 2,
		    		data: <%=monthlyStats.get(keyList.get(0)).values()%> 	
		    		}
		<%	
	    }
			if(keyList.size()>1){
				for(int i=1;i<keyList.size();i++){
					%>
					,{
			    		label: 'Sum Of Sales "<%=keyList.get(i)%>',
			    		backgroundColor: "rgba("+Math.floor(Math.random()*255)+ ", " +Math.floor(Math.random()*255)+", "+Math.floor(Math.random()*255) +",1)",
			    		barPercentage: 0.5,
			    		barThickness: 7,
			    		maxBarThickness: 30,
			    		minBarLength: 2,
			    		data: <%=monthlyStats.get(keyList.get(i)).values()%> 	
			    	}
			    <%
				}
				out.println("]},");
			}
			%>
	    options: {
	        scales: {
	        	 xAxes: [{
	        		   gridLines: {
	                       offsetGridLines: true
	                   }
	             }],
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	
	var makeChart = document.getElementById('makeStats');
	var makeBarChart = new Chart(makeChart, {
	    type: 'bar',
	    data:{
	    	labels: [<%boolean flag=true;
	    	for(String key:makeStats.keySet()){
	    		if(flag){
	    			out.println("'"+key+"'");
	    			flag=false;
	    			}
	    		else
	    			out.println(",'"+key+"'");
	    	}
	    			%>],
	    	datasets: [{
	    		label: 'Sum Of Sales',
	    		backgroundColor: "rgba("+Math.floor(Math.random()*255)+ ", " +Math.floor(Math.random()*255)+", "+Math.floor(Math.random()*255) +",1)",
	        	barPercentage: 0.5,
	        	barThickness: 20,
	        	maxBarThickness: 30,
	        	minBarLength: 2,
	        	data:<%=makeStats.values()%>
	    	
	    }]},
	    options: {
	        scales: {
	        	 xAxes: [{
	        		   gridLines: {
	                       offsetGridLines: true
	                   }
	             }],
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	</script>
</body>

</html>
