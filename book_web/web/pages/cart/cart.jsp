<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
	<script src="static/script/jquery-1.7.2.js" type="text/javascript"></script>
	<script type="text/javascript">


		//页面加载完成之后
		$(function () {
			//结账标签监听
			$(".getOrder").click(function () {
				count=$(this).parent().find(":first").val();
				if (count==0){
					alert("您的购物车中还没有商品！");
					return false;
				}
			});
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
			<span class="wel_word">购物车</span>
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
				<td>操作</td>
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
			<%@ page import="java.util.Collection" %><%
			User user = (User) session.getAttribute("user");
			ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute(user.getUsername() + ":shoppingCart");
			Collection<Goods> values = shoppingCart.getGoodsMap().values();

		%>
			<%
				for (Goods value : values) {%>
			<tr>

				<td><%=value.getName()%></td>
				<td>

					<input id="id" type="hidden" value="<%=value.getId()%>">
					<input id="preCount" type="hidden" value="<%=value.getCount()%>">
					<input class="updateCount"  type="text" value="<%=value.getCount()%>" width="10" size="10"></td>
				<td><%=value.getPrice()%></td>
				<td><%=value.getTotalPrice()%></td>
				<td><a class="deleteGoods" href="ShoppingServlet?action=delete&id=<%=value.getId()%>" ><%="删除"%></a></td>
			</tr>
			<%
				}
			%>

			

			
		</table>
		
		<div class="cart_info">

			<span class="cart_span">购物车中共有<span class="b_count"><%=shoppingCart.getTotalCount()%></span>件商品</span>
			<span class="cart_span">总金额<span class="b_price"><%=shoppingCart.getTotalPrice()%></span>元</span>
			<span class="cart_span"><a href="ShoppingServlet?action=clear">清空购物车</a></span>
			<span class="cart_span">
				<input type="hidden" value="<%=shoppingCart.getTotalCount()%>">
				<a class="getOrder" href="OrderServlet?action=createOrder">去结账</a>
			</span>
		</div>
	
	</div>

	<%--	页脚信息--%>
	<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>