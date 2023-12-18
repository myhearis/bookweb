<%@ page import="java.util.List" %>
<%@ page import="my.pojo.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单管理</title>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">订单管理系统</span>
		<%--			后台的菜单信息--%>
		<%@include file="/pages/Containspage/Background_menu.jsp"%>
	</div>

	<%
	//获取订单集合
		List<Order> orders = (List<Order>) request.getAttribute("orders");
	%>
	<div id="main">
		<table>
			<tr>
				<td>日期</td>
				<td>金额</td>
				<td>详情</td>
				<td>发货</td>
				
			</tr>
			<%
				for (Order order : orders) {%>
			<tr>
				<td><%=order.getCreaeTime()%></td>
				<td><%=order.getPrice()%></td>
				<td><a href="OrderServlet?action=showOrderDetails&orderId=<%=order.getOrderId()%>">查看详情</a></td>
				<td>
					<%
						String s="";
						if (order.getStatus()==0)
							s="等待收货";
						if (order.getStatus()==1)
							s="已收货";
					if (order.getStatus()==-1){%>
						<a href="OrderServlet?action=send&orderId=<%=order.getOrderId()%>">点击发货</a>
					<%}
					else {%>
						<%=s%>
				<%	}
					%>


				</td>
			</tr>
			<%	}
			%>


		</table>
	</div>

	<%--	页脚信息--%>
	<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>