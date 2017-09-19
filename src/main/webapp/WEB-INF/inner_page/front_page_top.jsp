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
	
	$(function(){
		$.post("front_page_top_show_yh_nch.do", function(data){
			/* 
			前台再次进行解码 
			decodeURI(URIstring)
			*/
			var yh_nch = decodeURIComponent(data);
		/* 	alert(yh_nch); */
			 $("#front_show_user_name").text(yh_nch);
		});
	})
	
</script>
<title>硅谷商城</title>
</head>
<body>
	<c:if test="${empty login_user }">
		<span id="front_show_user_name"></span>
		<a href="front_login_page.htm" >请登录</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="front_sign_in.htm" >注册</a>
		<a href="front_my_shopping_cart.do">我的购物车</a>
	</c:if>
	<c:if test="${!empty login_user }">
		<span style="color:red">欢迎:</span>${login_user.yh_nch }
		<a href="front_logout.do">登出</a>
		<a href="front_my_shopping_cart.do">我的购物车</a>
	</c:if>
</body>
</html>