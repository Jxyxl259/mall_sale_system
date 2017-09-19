<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	
</script>
<title>硅谷商城</title>
</head>
<body>

	<c:if test="${not empty session_car_list }">
		<c:forEach items="${session_car_list }" var="car">
			<div style="border: solid;">
				<img alt="mini_car_item_img" src="upload_image/${car.shp_tp}"
					style="width: 10%"> 
				<br> 
				${car.sku_mch }&nbsp;&nbsp;&nbsp;&nbsp;单价：${car.sku_jg }
				<br> 
				${car.tjshl }&nbsp;&nbsp;&nbsp;&nbsp;合计金额：${car.hj }
			</div><br> 
		</c:forEach>
	</c:if>
	<c:if test="${empty session_car_list }">
		<h3>购物车中没有任何商品</h3>
	</c:if>
	
</body>
</html>