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
	
	function submit_order (){
		var address_id = $(":radio:checked").attr("id");
		var shjr = $(":radio:checked").attr("class");
		var lxfsh = $(":radio:checked").attr("alt");
		var address_info = $(":radio:checked").val();
		alert(address_id+"  "+shjr+"   "+lxfsh+"   "+address_info);
		
		window.location.href="submit_order.do?user_address_id="+address_id+"&user_address_info="+address_info+"&shhr="+shjr+"&lxfsh="+lxfsh;
	}
	
</script>
<title>硅谷商城</title>
</head>
<body>
	<!-- 用户确认订单的页面 -->
	<h2>确认订单信息</h2>
	<c:forEach items="${user_address_list }" var="address">
		<input type="radio" name="address" ${address.dzzt == 1?"checked":"" } id="${address.id}" value="${address.dz_mch }" class="${address.shjr }" alt="${address.lxfsh }"/>${address.dz_mch } &nbsp;&nbsp;&nbsp;${address.shjr }<br>
	</c:forEach>
	<br>
	<br>
	<c:forEach items="${order_list }" var="order" varStatus="index">
		<div style="border: solid;">
			<div style="font-size: 20px">${index.index+1 }号订单的总金额：${order.zje }</div>
			<hr>
			<br>
			<c:forEach items="${order.order_info_list }" var="order_info">
					<img alt="order_info_img" src="upload_image/${order_info.shp_tp }" style="width: 10%"><br>
					商品名称${order_info.sku_mch }<br>
					商品价格${order_info.sku_jg }<br>
					商品数量${order_info.sku_shl }<br>
			</c:forEach>
		</div>
		<br>
	</c:forEach>
	<hr>
	<a href="javascript:submit_order();">提交订单</a>
	
</body>
</html>