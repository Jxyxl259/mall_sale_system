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


	$(function(){
		$(":checkbox").each(function(i,json){
			var is_checked = json.value;
			if(is_checked == '1'){
				json.checked = true;
			}else{
				json.checked = false;
			}
		});
		
	});
	//根据购物车中每条购物项信息是否选中，通过ajax向后台发送异步请求，更新数据库数据，刷新当前页面内容
	//需要向后台传递要更新的数据，分为用户已登录状态和用户未登录状态，
	//已登录状态下，向后台传递用户id,购物车id,库存id
	//未登录状态下，向后台传递的只有库存id:sku_id
	function front_car_if_checked(on_checked) {
		var sku_id = $(on_checked).next(":eq(0)").val();
		/* alert(sku_id); */
		if (on_checked.checked) {//该购物项处于选中状态
			$.post("front_change_car_item_checked.do", {
				"sku_id" : sku_id,//通过库存sku_id找到session或cookie集合中对应的购物项，然后更新数据，同步session更新数据库 
				"shfxz" : 1
			}, function(data) {
				$("#front_shopping_car_inner_data_div").html(data);
			});
		} else{
			$.post("front_change_car_item_checked.do", {
				"sku_id" : sku_id,//通过库存sku_id找到session或cookie集合中对应的购物项，然后更新数据，同步session更新数据库 
				"shfxz" : 0
			}, function(data) {//该购物项处于未勾选状态 
				$("#front_shopping_car_inner_data_div").html(data);
			});
		}
	};
	
	//通过加减号调整购物车中购物项的数量，
 	function front_modify_car_item_num(yh_id,sku_id,car_id,add_or_del){
		alert(yh_id+"  "+sku_id+"  "+car_id+"  "+add_or_del);
		
		 var param_data = {
				"yh_id":yh_id,
				"sku_id":sku_id,
				"car_id":car_id 
		};
		
		if(add_or_del == 1){
			param_data.num = 1
		}else{
			param_data.num = -1
		}
		/* alert(param_data.num); */
		$.ajax(	{url:"front_car_item_num_change.do",
				data:param_data, 
				success:function(data){
					$("#front_shopping_car_inner_data_div").html(data);
				} 
		});  
	}

	//通过输入框调整购物车中购物项的数量 
	function front_modify_car_item_num_by_input(){
		
	}
</script>
<title>硅谷商城</title>
</head>
<body>
	<div id="">
		<c:forEach items="${front_shopping_cars }" var="car">
			<div style="border: solid;">
				<input type="checkbox" value="${car.shfxz }" onclick="front_car_if_checked(this)" /> 
				<input type="hidden" value="${car.sku_id }"/>
				<input type="hidden" value="${car.yh_id }"/>
				<input type="hidden" value="${car.id }"/>
				<img alt="front_shopping_car_item_img" src="upload_image/${car.shp_tp}" style="width: 10%"> 
				<br> ${car.sku_mch }&nbsp;&nbsp;&nbsp;&nbsp;单价：${car.sku_jg }
				<br>数量： <a href="javascript:;" onclick="front_modify_car_item_num(${car.yh_id },${car.sku_id },${car.id },-1)">-</a>
						 <input type="text" value="${car.tjshl }" style="width: 10px" onchange="front_modify_car_item_num(this)"/>
						 <a href="javascript:;" onclick="front_modify_car_item_num(${car.yh_id },${car.sku_id },${car.id },1)">+</a>&nbsp;&nbsp;&nbsp;&nbsp;合计金额：${car.hj }
			</div>
			<br>
		</c:forEach>
		<hr>								<!-- 取结算页面，生成游离态订单 -->
		<h3>总金额￥：${cart_total_price}</h3><a href="front_goto_checkout_page.do">去结算</a>
	</div>
</body>
</html>