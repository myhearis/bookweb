<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>尚硅谷会员注册页面</title>
		<%--	静态包含引用样式与base标签--%>
		<%@include file="/pages/Containspage/base_quote.jsp"%>
		<script src="static/script/jquery-1.7.2.js" type="text/javascript"></script>
		<script type="text/javascript" >

			/*书城项目第一阶段，表单验证
			验证用户名:必须由字母，数字下划线组成，并且长度为5到12位
			验证密码:必须由字母，数字下划线组成，并且长度为5到12位验证
			确认密码:和密码相同
			邮箱验证: xxxxx.omm
			验证码:现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
			*/

			// 页面加载完成之后
			$(function () {
				<%--//给验证码图片绑定单击事件--%>
				$("img.codeimg").click(function () {
					this.src="<%=baseStr%>KaptchaServlet.jpg?d=<%=LocalDateTime.now().toString()%>";
				});
				//添加注册按钮点击事件
				$("#sub_btn").click(function () {
					var tip= $(".errorMsg");//获取错误提示框

					//验证用户名id=username
					let username = $("#username").val();//用户名
					let password = $("#password").val();//密码
					let surepassword = $("#repwd").val();//确认密码
					let email = $("#email").val();//邮箱
					let code = $("#code").val();//输入的验证码
					var test=/^\w{5,12}$/;//正则表达式
					if (!test.test(username)){
						//显示错误信息
						tip.text("用户名不合法！")
						//禁止提交
						return false;
					}
					//擦除提示信息
					tip.text("");
					//验证密码
					if (!test.test(password)){
						//显示错误信息
						tip.text("密码不合法！")
						//禁止提交
						return false;
					}
					//擦除提示信息
					tip.text("");
					//确认密码
					if (password!=surepassword){
						//显示错误信息
						tip.text("密码与确认密码不一致！")
						//禁止提交
						return false;
					}
					//擦除提示信息
					tip.text("");
					//验证邮箱
					var sureemail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
					if (!sureemail.test(email)){
						//显示错误信息
						tip.text("邮箱不合法！")
						//禁止提交
						return false;
					}
					//擦除提示信息
					tip.text("");
					//验证码  验证
					if (code.length==0){
						//显示错误信息
						tip.text("验证码不能为空！")
						//禁止提交
						return false;
					}
					//擦除提示信息
					tip.text("");
				});
				//添加用户名框的改变事件（用于判断用户名是否存在）
				$("#username").change(function () {
					var name= $(this);//获取dom对象
					//发起请求
					$.post("http://localhost:8888/book/UserAction","action=ajaxUsernameIsExist&username="+name.val(),function (data) {
						var msg="";
						//如果用户名已存在
						if(data){
							msg="该用户名已存在";
						}
						$("#errorMsg").text(msg);//显示信息
					},"json");
				})

			});

		</script>
	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>
	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg" id="errorMsg">${requestScope.message}</span>
							</div>
							<div class="form">
<!--								<form action="regist_success.jsp">-->
<!--								<form action="pages/user/regist_success.jsp">-->
<!--								先将信息发送给Servlet小程序，再由小程序决定是否注册成功并跳转-->
								<form action="UserAction" method="post">
									<input type="hidden" name="action" value="regist">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username" id="username"  value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址" value="${requestScope.email}"
										   autocomplete="off" tabindex="1" name="email" id="email" />
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 120px;" id="code" />
									<img class="codeimg" id="codeimg" alt="" src="KaptchaServlet.jpg"  width="120" style="float: right; margin-right: 40px">
									<br />
									<br />
									<input  href="." type="submit" value="注册" id="sub_btn" />
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