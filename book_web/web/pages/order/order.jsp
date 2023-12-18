<%@ page import="java.util.List" %>
<%@ page import="my.pojo.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
	<script src="static/script/jquery-1.7.2.js" type="text/javascript"></script>
	<script type="text/javascript">
		//页面加载完成之后
		$(function () {
			//确认收货
			$("a.suerReceive").click(function () {
				//获取当前订单状态
				status = $(this).parent().find(":first").val();
				if (status==-1){
					if (confirm("当前订单还未发货，您确定要签收吗?")){
						return true;
					}
					else return false;
				}
				if (status==1){
					alert("您已经签收过该订单啦！工具人也很累的呀- -");
					return false;
				}
				return true;
			})
		})
	</script>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">我的订单</span>
<%--			<div>--%>
<%--				<span>欢迎<span class="um_span">韩总</span>光临尚硅谷书城</span>--%>
<%--				<a href="../order/order.jsp">我的订单</a>--%>
<%--				<a href="../../index.jsp">注销</a>&nbsp;&nbsp;--%>
<%--				<a href="../../index.jsp">返回</a>--%>
<%--			</div>--%>
		<%--			静态包含的欢迎菜单信息--%>
		<%@include file="/pages/Containspage/welcome.jsp"%>
	</div>
	
	<div id="main">
		
		<table>
			<tr>
				<td>日期</td>
				<td>金额</td>
				<td>状态</td>
				<td>详情</td>
			</tr>

			<%
				List<Order> orders = (List<Order>) request.getAttribute("orders");
				for (Order order : orders) {

			%>
					<tr>
				<td><%=order.getCreaeTime()%></td>
				<td><%=order.getPrice()%></td>
				<td><%=order.getStatusString()%></td>
				<td>
					<input type="hidden" id="orderId" value="<%=order.getOrderId()%>">
					<a class="showDetail" href="OrderServlet?action=showOrderDetails&orderId=<%=order.getOrderId()%>">查看详情</a></td>
						<td>
							<input type="hidden" value="<%=order.getStatus()%>">
							<a class="suerReceive" href="OrderServlet?action=receive&orderId=<%=order.getOrderId()%>">确认收货</a></td>
			</tr>
				<%}
			%>


		</table>
		
	
	</div>
	
<%--	<div id="bottom">--%>
<%--		<span>--%>
<%--			尚硅谷书城.Copyright &copy;2015--%>
<%--		</span>--%>
<%--	</div>--%>
<%--	页脚信息--%>
<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>