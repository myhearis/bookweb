<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单详情</title>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
	<script src="static/script/jquery-1.7.2.js" type="text/javascript"></script>
	<script type="text/javascript">
		//页面加载完成之后
		$(function () {
			//给显示数量的所有文本框添加改变内容事件
			$(".updateCount").change(function () {
				//获取修改前的数量
				preCount = $(this).parent().find("#preCount").val();
				//获取修改后的数量
				count=$(this).val();
				//获取隐藏域中的id
				id=$(this).parent().find("input:first").val();
				//判断修改的数据是否大于0
				if (count>=1){
					//跳转到ShoppingServlet，传入对应的方法参数
					location.href="${pageScope.basePath}ShoppingServlet?action=updateCount&id="+id+"&count="+count;
				}
				else {
					alert("商品数量最少为1,修改失败！");
					//重置为原来的数量
					$(this).val(preCount);
				}
			});
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">订单详情</span>
<%--			<div>--%>
<%--				<span>欢迎<span class="um_span">韩总</span>光临尚硅谷书城</span>--%>
<%--				<a href="pages/order/order.jsp">我的订单</a>--%>
<%--				<a href="index.jsp">注销</a>&nbsp;&nbsp;--%>
<%--				<a href="index.jsp">返回</a>--%>
<%--			</div>--%>
		<%--			静态包含的欢迎菜单信息--%>
		<%@include file="/pages/Containspage/welcome.jsp"%>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
			</tr>
<%--			<%@include file="/pages/Containspage/ShoppingView.jsp"%>--%>
<%--			<tr>--%>
<%--				<td>时间简史</td>--%>
<%--				<td>2</td>--%>
<%--				<td>30.00</td>--%>
<%--				<td>60.00</td>--%>
<%--				<td><a href="#">删除</a></td>--%>
<%--			</tr>	--%>

			<%@ page import="com.mysql.cj.x.protobuf.MysqlxDatatypes" %>
			<%@ page import="my.pojo.ShoppingCart" %>
			<%@ page import="my.pojo.User" %>
			<%@ page import="my.pojo.Goods" %>
			<%@ page import="java.util.Collection" %>
			<%@ page import="my.pojo.OrderItem" %>
			<%@ page import="java.util.List" %><%

			Collection<OrderItem> values = (List<OrderItem>)request.getAttribute("orderItemList");
		%>
			<%
				for (OrderItem value : values) {%>
			<tr>
				<td><%=value.getName()%></td>
				<td>
					<input id="id" type="hidden" value="<%=value.getId()%>">
					<input id="preCount" type="hidden" value="<%=value.getCount()%>">
					<input disabled="disabled" class="updateCount"  type="text" value="<%=value.getCount()%>" width="10" size="10"></td>
				<td><%=value.getPrice()%></td>
				<td><%=value.getTotalPrice()%></td>
			</tr>
			<%
				}
			%>

			

			
		</table>
		
		<div class="cart_info">

<%--			<span class="cart_span">当前订单中共有<span class="b_count"><%=%></span>件商品</span>--%>
<%--			<span class="cart_span">总金额<span class="b_price"><%=%></span>元</span>--%>

		</div>
	
	</div>

	<%--	页脚信息--%>
	<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>