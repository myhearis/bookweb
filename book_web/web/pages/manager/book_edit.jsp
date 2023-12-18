<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
	
	input {
		text-align: center;
	}
</style>
</head>
<body>
		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">编辑图书</span>
<%--			<div>--%>
<%--				<a href="book_manager.jsp">图书管理</a>--%>
<%--				<a href="order_manager.jsp">订单管理</a>--%>
<%--				<a href="../../index.jsp">返回商城</a>--%>
<%--			</div>--%>
<%--			后台的菜单信息--%>
			<%@include file="/pages/Containspage/Background_menu.jsp"%>
		</div>
		
		<div id="main">
			<form action="manager/BookServlet" method="post">
				<table>

					<tr>
						<td>名称</td>
						<td>价格</td>
						<td>作者</td>
						<td>销量</td>
						<td>库存</td>
						<td colspan="2">操作</td>
					</tr>
					<%
						//动态获取传入的action的值，来控制具体访问BookServlet中的哪一个方法
						String action = request.getParameter("action");
						//获取修改信息传来的当前页码
						String pageNoUpdate = (String) request.getAttribute("pageNo");
					%>
					<tr>
						<td><input name="name" type="text" value="${requestScope.updatebook.name}"/></td>
						<td><input name="price" type="text" value="${requestScope.updatebook.price}"/></td>
						<td><input name="author" type="text" value="${requestScope.updatebook.author}"/></td>
						<td><input name="sales" type="text" value="${requestScope.updatebook.sales}"/></td>
						<td><input name="stock" type="text" value="${requestScope.updatebook.stock}"/></td>
						<td><input type="hidden" name="action" value="<%=action%>"/></td>
<%--						将对象的id一起传给BookServlet程序--%>
						<td><input type="hidden" name="id" value="${requestScope.updatebook.id}"/></td>
						<td><input type="hidden" name="pageNo" value="<%=request.getParameter("pageNo")%>"/></td>
						<td><input type="hidden" name="pageNoUpdate" value="<%=pageNoUpdate%>"/></td>
						<td><input type="submit" value="提交"/></td>
					</tr>
				</table>
			</form>
			
	
		</div>

		<%--	页脚信息--%>
		<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>