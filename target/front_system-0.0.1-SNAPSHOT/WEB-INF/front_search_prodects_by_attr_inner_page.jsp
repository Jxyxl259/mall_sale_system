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

	<c:forEach items="${products_list}" var="product_info">
		<div
			style="float: left; border: solid; width: 250px; height: 300px; margin-left: 20px; margin-right: 20px;margin-bottom: 20px;cursor: pointer;"
			onclick="window.open('front_show_product_detail.do?sku_id=${product_info.id}&spu_id=${product_info.spu.id }')">
			<img alt="shp_tp" src="upload_image/${product_info.spu.shp_tp }"
				style="width: 230px; height: 150px; margin-top: 10px; margin-left: 10px"><br>
			<br> <span>￥${product_info.jg }</span><br>
			<br> <span>${product_info.spu.shp_mch}${product_info.sku_mch }</span>
		</div>
	</c:forEach>

</body>
</html>