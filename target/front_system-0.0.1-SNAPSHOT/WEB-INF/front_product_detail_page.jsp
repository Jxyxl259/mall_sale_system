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
	
	function front_add_shopping_cart(){
		$("#front_add_shoppingcar_form").submit();
	}
	
</script>
<title>硅谷商城</title>
</head>
<body><!-- 显示商品的spu名称+sku名称 -->
	商品名称：<span>${product_detail.spu.shp_mch }[${product_detail.sku_mch }]</span><br>
	商品价格：<span>${product_detail.jg }</span><br>
	商品库存：<span>${product_detail.kc }</span><br>
	商品销量：<span>${product_detail.sku_xl }</span><br>
	<a href="javascript:;" onclick="front_add_shopping_cart()">添加购物车</a>
	<form id="front_add_shoppingcar_form" action="front_add_shopping_car.do" method="post">
		<input type="hidden" name="sku_mch" value="${product_detail.sku_mch }">
		<input type="hidden" name="sku_jg" value="${product_detail.jg }"/>
		<input type="hidden" name="hj" value="${product_detail.jg }"/>
		<input type="hidden" name="shp_id" value="${product_detail.shp_id }"/>
		<input type="hidden" name="tjshl" value="1"/>
		<input type="hidden" name="sku_id" value="${product_detail.id }"/>
		<input type="hidden" name="shp_tp" value="${product_detail.spu.shp_tp }"/>
		<input type="hidden" name="kcdz" value="${product_detail.kcdz }"/>
		<input type="hidden" name="shfxz" value="1"/>
	</form>	
	<hr><!-- 此处应为同类商品信息展示区 -->
		同类商品信息展示区
	<hr><!-- 商品图片展示区 -->
	<div>
		<c:forEach items="${product_detail.images }" var="image">
			<img alt="shp_tp" src="upload_image/${image.url }" style="width: 30%;">
		</c:forEach>
	</div>
	<hr><!-- 商品属性信息展示 -->
	<div>
		<c:forEach items="${product_detail.sku_av_list }" var="attr">
			${attr.shxm_mch }:${attr.shxzh_mch }<br>
		</c:forEach>
	</div>
	
</body>
</html>