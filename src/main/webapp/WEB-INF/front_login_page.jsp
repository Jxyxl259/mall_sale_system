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
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	
</script>
<title>硅谷商城</title>
</head>
<body>
	
	<form action="front_user_login.do" method="post">
	
		用户昵称：<input type="text" name="yh_nch"/>
		<br>
		用户密码：<input type="text" name="yh_mm" />
		<br>
		<input type="radio" name="datasourceswitch" value="1"/>商城用户
		<input type="radio" name="datasourceswitch" value="2"/>用户系统
		<input type="submit" value="登录" />
		
	</form>
	
	
</body>
</html>