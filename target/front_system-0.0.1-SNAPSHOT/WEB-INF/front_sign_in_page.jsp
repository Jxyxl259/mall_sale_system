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
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function(){
		
		
		
	});
	
	function front_get_verify_code(){
		
		
	}
	
	function front_sign_in(){
		
		
		
	};
</script>
<title>硅谷商城</title>
</head>
<body>
	<div class="container">

		<form action="front_sign_in_new_user.do" class="form-signin" role="form" method="post">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-log-in"></i> 用户注册
			</h2>
			<div class="form-group has-success has-feedback">
				<input type="text" name="yh_nch" class="form-control" id="front_sign_in_yh_nch"
					placeholder="请输入账号" autofocus> <span
					class="glyphicon glyphicon-user form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="text" name="yh_xm" class="form-control" id="front_sign_in_yh_xm"
					placeholder="请输入真实姓名" style="margin-top: 10px;"> <span
					class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="text" name="yh_mm" class="form-control" id="front_sign_in_yh_mm"
					placeholder="请输入您的密码" style="margin-top: 10px;"> <span
					class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="text" name="yh_shjh" class="form-control" id="front_sign_in_yh_shjh"
					placeholder="请输入手机号" style="margin-top: 10px;"> <button id="front_sign_in_verify_code_btn" onclick="front_get_verify_code">点击获取验证码</button>
					<span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
				<input type="text"  class="form-control" id="front_sign_in_verify_code_input"
					placeholder="请输入收到的验证码" style="margin-top: 10px;" >
			</div>
			<div class="checkbox">
				<label> 忘记密码 </label> <label style="float: right"> <a
					href="front_user_login.htm">我有账号</a>
				</label>
			</div>
			<!-- <a class="btn btn-lg btn-success btn-block" href="member.htm">注册</a> -->
			<button class="btn btn-lg btn-success btn-block" style="display: none" id="front_sign_in_btn" onclick="front_sign_in()">注册</button>
		</form>
	</div>
</body>
</html>