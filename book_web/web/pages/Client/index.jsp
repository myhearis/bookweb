<%@ page import="my.pojo.Page" %>
<%@ page import="my.pojo.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="my.utils.ShoppingUtils" %>
<%@ page import="my.pojo.ShoppingCart" %>
<%@ page import="my.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>书城首页</title>
	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
	<script src="static/script/jquery-1.7.2.js" type="text/javascript"></script>
	<script>
		//页面加载完成后
		$(function () {
			//给加入购物车按钮添加监听事件
			$("button.AddShopping").click(function () {
				//如果用户未登录，则询问是否登录
				if (${empty sessionScope.user}){
					if (confirm("您还没有登录，去登录?")){
						location.href="${pageScope.basePath}pages/user/login.jsp"
					}
				}
				//用户已登录的情况
				else {

					//获取书的id
					bookId=$(this).parent().find("input:first").val()
					//获取书名
					name=$(this).parent().find("#name").val()
					//获取书的单价
					price=$(this).parent().find("#price").val()
					//跳转到购物模块的Servlet程序执行对应的方法
					pageurl="${requestScope.pageurl}&pageNo=${requestScope.pageObj.pageNo}";

					//ajax请求
					var ajaxUrl="${pageScope.basePath}ShoppingServlet";
					var urlData="action=ajaxAdd&name="+name+"&price="+price+"&id="+bookId+"&pageurl="+pageurl;
					$.get(ajaxUrl,urlData,function (map) {
						//显示信息
						$("#shoopingCartCount").text("您的购物车中有"+map.shoppingCartCount+"件商品");
						$("#addGoodsName").text(map.goodsName);
					},"json");
					//location.href="${pageScope.basePath}ShoppingServlet?action=add&name="+name+"&price="+price+"&id="+bookId+"&pageurl="+pageurl;
				}
			})
			//监听跳转页码
			$("input.selectpage").click(function () {
				//获取文本信息
				no = $("input.kk").val();
				pageTotal=${requestScope.pageObj.pagetTotal};
				if (no<0||no>pageTotal){
					alert("您输入的页码有误！")
				}
				else
				//动态web域中的当前地址地址
				location.href="${pageScope.basePath}Client/ClientServlet?action=${requestScope.action}&pageNo="+no;
			})
		});
	</script>
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">网上书城</span>
			<div>
				<%
				if (session.getAttribute("user")==null){%>
					<a href="pages/user/login.jsp">登录</a> |
					<a href="pages/user/regist.jsp">注册</a>
							<%
				}
				%>
				<%
				if (session.getAttribute("user")!=null) {%>
				<span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
				<a href="OrderServlet?action=getMyOrders">我的订单</a>
				<a href="UserAction?action=logout">注销</a>&nbsp;&nbsp;
				<a href="index.jsp">返回</a>
					<a href="pages/cart/cart.jsp?d=<%=LocalDateTime.now().toString()%>">购物车</a>
					<%
						User user= (User) request.getSession().getAttribute("user");
						if ("helloworld".equals(user.getUsername())){%>
						<a href="pages/manager/manager.jsp">后台管理</a>
				<%
						}

					%>

				<%
				}
				%>


			</div>
	</div>
	<div id="main">
		<div id="book">
			<div class="book_cond">
				<form action="Client/ClientServlet" method="post">
					价格：<input id="min" type="text" name="min" value="${requestScope.min}"> 元 -
						<input id="max" type="text" name="max" value="${requestScope.max}"> 元
						<input type="submit" value="查询" />
					<a href="Client/ClientServlet?action=page">查看全部</a>
					<input type="hidden" name="action" value="pageByPrice">
				</form>
			</div>
			<%
				String name="";
				String type="";
				int count=0;
				ShoppingCart userShoppingCart = ShoppingUtils.getUserShoppingCart(request.getSession());
				if (userShoppingCart!=null&&userShoppingCart.getNewGood()!=null){
					name=userShoppingCart.getNewGood().getName();
					count=userShoppingCart.getTotalCount();
				}


			%>
			<%
			if (request.getSession().getAttribute("user")!=null){%>
				<div  style="text-align: center" >
				<span id="shoopingCartCount">您的购物车中有<%=count%>件商品</span>
			<div>
				您刚刚将<span style="color: red" id="addGoodsName"><%=name%></span>加入到了购物车中
			</div>
		</div>
			<%}
			%>


			

<%--			图书显示信息--%>
<%
	Page<Book> pageObj = (Page<Book>) request.getAttribute("pageObj");
	List<Book> list = pageObj.getList();
	for (Book book : list) {
		%>
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="<%=book.getImgPath()%>" />
				</div>
				<div class="book_info">

					<div class="book_name" >
						<span class="sp1">书名:</span>
						<span class="sp2" id="bookName<%=book.getId()%>"><%=book.getName()%></span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2"><%=book.getAuthor()%></span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥<%=book.getPrice()%></span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2"><%=book.getSales()%></span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2"><%=book.getStock()%></span>
					</div>
					<div class="book_add" >
						<input id="id" type="hidden" name="id" value="<%=book.getId()%>">
						<input id="name" type="hidden" name="bookName" value="<%=book.getName()%>">
						<input id="price" type="hidden" name="bookPrice" value="<%=book.getPrice()%>">
						<button class="AddShopping">加入购物车</button>
					</div>
				</div>
			</div>

			<%
	}

%>

			

		</div>
		
<%--		<div id="page_nav">--%>
<%--		<a href="#">首页</a>--%>
<%--		<a href="#">上一页</a>--%>
<%--		<a href="#">3</a>--%>
<%--		【4】--%>
<%--		<a href="#">5</a>--%>
<%--		<a href="#">下一页</a>--%>
<%--		<a href="#">末页</a>--%>
<%--		共10页，30条记录 到第<input value="4" name="pn" id="pn_input"/>页--%>
<%--		<input type="button" value="确定">--%>
<%--		</div>--%>
		<%@include file="/pages/Containspage/getNewPages.jsp"%>
	</div>

	<%--	页脚信息--%>
	<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>