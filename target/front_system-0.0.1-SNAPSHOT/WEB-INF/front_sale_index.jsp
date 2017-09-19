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
	$.getJSON("jquery/json/class_1.js",function(jsonCollection) {
			$.each(jsonCollection, function(i, class_1_info) {
				$("#front_page_product_classification_1_ul")
					.append("<li value="+class_1_info.id+" onmouseover='front_show_classification_2_info_by_1_id(this.value)' style='width:70px'>"+class_1_info.flmch1+"</li>");
			});
	});
	
	function front_show_classification_2_info_by_1_id(class_1_id){
		/* alert(class_1_id); */
 		$("#front_page_product_classification_2_ul").empty();
		$.getJSON("jquery/json/class_fl_2_"+class_1_id+".js",function(jsonCollection) {
			$.each(jsonCollection, function(i, class_2_info) {
				$("#front_page_product_classification_2_ul")
					.append("<li value="+class_2_info.id +" style='width:100px'><a href='javascript:;' onclick=front_load_product_info_by_class_2_id(this) >"+class_2_info.flmch2+"</a></li>");
			});
		});
	}
	
	function front_load_product_info_by_class_2_id(fl2){
		var flmch2 = $(fl2).parent();
		var class_2_id = flmch2.val();
		var flmch2 = flmch2.text();
		window.location.href="front_index_get_products_info_by_class_2_id.do?class_2_id="+class_2_id+"&flmch2="+flmch2;
	}
</script>
<title>硅谷商城</title>
</head>
<body>
	<jsp:include page="inner_page/front_page_top.jsp"></jsp:include>
	
	<jsp:include page="inner_page/mini_car_page.jsp"></jsp:include>
	<!-- 异步加载商品一二级分类数据 -->
	<ul id="front_page_product_classification_1_ul">
		
	</ul>
	<br>
	<ul id="front_page_product_classification_2_ul">
		
	</ul>
	


</body>
</html>