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
	<h2>支付服务页面</h2>
	<a href="front_pay.do">点击付款</a>
<!-- 	
		实际情况是当点击付款按钮时，向企业内部的交易系统提交交易金额和用户的账户信息（付款方的信息）， 
		然后企业将企业账户（收款方的信息/交易金额/付款方的账号/第三方支付平台的签名）通过重定向的方式发送到第三方支付平台
		页面会跳转到第三方支付平台（支付宝支付页面）
		在第三方交易平台处理完交易业务之后，再次重定向回企业内部的交易系统页面，此时页面会显示付款成功3秒后跳转到订单页面这类的信息
		在第三方交易平台重定向回企业内部的交易系统之后，（付款成功的情况下），企业内部交易平台会重定向到交易平台，
		确认商品是否可以购买（库存是否充裕）（如何解决超卖问题，使用select for update,加查询行锁，以解决）
		
-->
</body>
</html>