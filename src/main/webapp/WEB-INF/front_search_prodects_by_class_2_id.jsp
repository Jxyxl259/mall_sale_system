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

	

	function search_products_by_attr(){
		/* alert("！！！"); */
		var class_2_id = "${class_2_id}";
		var checked_list = $(":input").filter(":checked");//获取所有选中的input_checkbox节点，这是一个jQ对象数组
		/* if(checked_list.length == 0 ){
			window.location.href = "front_index_get_products_info_by_class_2_id.do?class_2_id=${class_2_id}";
		}  */
		
		var attr_list = new Array();//创建一个js集合对象
		$(checked_list).each(function(i,json){//遍历获取到的选中的input_checkbox对象
			//alert(json.value);//获取每个jQ对象中的value属性的属性值
			attr_list[i] = json.value;//每个jQ对象中的value属性的属性值封装到一个js集合对象中
		});
		
		//排序条件
		var order_condition = $("#order_by_condition").val();
		//alert("异步查询条件"+order_condition);
 		$.post("search_products_by_attr_and_attr_value.do",{"class_2_id":class_2_id,"attr_list":attr_list,"order_condition":order_condition},function(data){
			
			$("#front_page_search_by_attr_inner_page").empty();
			$("#front_page_search_by_attr_inner_page").append(data);
			/* alert("跳过来了，内嵌页中应该有数据！！！！"); */
		}); 
	};
	
	function front_order_by( new_order_condition ){
		alert("点击排序超链接后穿过来的排序条件"+new_order_condition);
		var old_order_condition = $("#order_by_condition").val();
		alert("old_order_condition"+old_order_condition);
		if(new_order_condition == old_order_condition){
			new_order_condition += " desc ";
		}
		$("#front_condition_hide_area").html('<input id="order_by_condition" type="hidden" value="'+new_order_condition+'"/>');
		alert(new_order_condition);
		search_products_by_attr();
	};
</script>
<title>硅谷商城</title>
</head>
<body>
	${flmch2 }	
	<jsp:include page="inner_page/mini_car_page.jsp"></jsp:include>
	<hr>
	<div id="front_condition_hide_area" style="display: none">
		<input id="order_by_condition" type="hidden" value=" order by jg "/>
	</div>
	<form id="front_attr_checkbox">
		<c:forEach items="${attr_list }" var="attrinfo"><!-- tma表属性id   		tma属性名名称 -->
			<span style="font-size: 25px" id="t_mall_attr_${attrinfo.id }">${attrinfo.shxm_mch }</span>:
								<!-- tmv类型集合 -->		<!-- tmv对象 -->
			<c:forEach items="${attrinfo.valueList }" var="attr_value">
				<input type="checkbox" 
					   name="front_project_attr" 
					   value="${attr_value.shxm_id }_${attr_value.id }"
					   id="t_mall_sku_attr_value_id_${attr_value.id }"
					   onclick="search_products_by_attr()"/>
					${attr_value.shxzh }${attr_value.shxzh_mch }
			</c:forEach>
			<!-- t_mall_attr表的主键属性id:${attrinfo.id } | t_mall_value表关联字段关联t_mall_attr表的id主键${attr_value.shxm_id } 
					这两个是一个东西		
			-->
			<br>
		</c:forEach>
	</form>
	<hr>
	<a href="javascript:;" onclick="front_order_by(' order by jg ')">价格</a>
	<a href="javascript:;" onclick="front_order_by(' order by kc ')">库存</a>
	<a href="javascript:;" onclick="front_order_by(' order by sku_xl ')">销量</a>
	<a href="javascript:;" onclick="front_order_by(' order by sku_av.chjshj ')">上架时间</a>
	<hr>
	
	<div id="front_page_search_by_attr_inner_page">
		<!-- 商品详情内嵌页 -->
		<jsp:include page="front_search_prodects_by_attr_inner_page.jsp"></jsp:include>
	</div>

</body>
</html>