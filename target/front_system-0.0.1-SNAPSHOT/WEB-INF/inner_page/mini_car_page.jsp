<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	
  	function mini_car_show(){
  		$.post("mini_car_data_apply.do", function(data){
  		   $("#mini_car_inner_data_page").html(data);
  		 });
  		$("#mini_car_inner_data_page").show();
  	}
	
  	function mini_car_hide(){
  		
  		$("#mini_car_inner_data_page").hide();
  	}
</script>
<title>硅谷商城</title>
</head>
<body>
	<!-- 点击跳转到购物车页面 -->
	<a href="front_my_shopping_cart.do" onmouseout="mini_car_hide()" onmouseover="mini_car_show()" style="float: right">迷你购物车 </a>
	<div id="mini_car_inner_data_page" style="margin-left: 700px;width: 500px">
		
	</div>
</body>
</html>