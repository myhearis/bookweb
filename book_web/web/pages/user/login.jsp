<%@ page import="my.utils.CookieUtils" %>
<%@ page import="jakarta.servlet.http.Cookie" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员登录页面</title>
<%--	<base href="http://localhost:8888/book/">--%>
<%--<link type="text/css" rel="stylesheet" href="static/css/style.css" >--%>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎登录</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>尚硅谷会员</h1>
<!--								<a href="regist.jsp">立即注册</a>-->
								<a href="pages/user/regist.jsp">立即注册</a>
							</div>
							<div class="msg_cont">
								<b></b>
<%--								使用el表达式，可以不用考虑null的情况，这时候不会输出--%>
								<span class="errorMsg">${requestScope.message}</span>
							</div>
							<div class="form">
								<form action="UserAction" method="post">
									<input type="hidden" value="login" name="action">
									<label>用户名称：</label>
									<%
										Cookie bookUserName = CookieUtils.getCookie("BookUserName", request.getCookies());
									String username="";
									if (bookUserName!=null)
										username=bookUserName.getValue();
									%>
									<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username"  value="<%=username%>"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" />
									<br />
									<br />
									<input href="." type="submit" value="登录" id="sub_btn" />
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%--	页脚信息--%>
		<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>
