<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>

	<%--	静态包含引用样式与base标签--%>
	<%@include file="/pages/Containspage/base_quote.jsp"%>
	<script src="static/script/jquery-1.7.2.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function (){
			//删除标签监听器
			$("a.deleteid").click(function () {
				//这个函数是找出正在处理的元素的后代元素的好方法。find
				return  confirm("确定删除《"+$(this).parent().parent().find("td:first").text()+"》吗？");
			});
			$("input.selectpage").click(function () {
				//获取文本框内容
				str = $("input.kk").val();
				//js中提供了一个地址栏对象location，他的href属性可以获取地址栏的数据，也可以修改
				//从pageContext域中获取当前设备的base值
				//获取当前的总页码，防止超过总页码
				pageTotal=${requestScope.pageObj.pagetTotal};

				if(str<=pageTotal&&str>=1)
					location.href="${pageScope.basePath}manager/BookServlet?action=page&pageNo="+str;
				else alert("您输入了错误的页码");

			})

		})
		//上一页标签监听方法
		function pre(thisPageNo) {

			if (thisPageNo==1){
				alert("第一页");
				return false;
			}
			alert("非1");
			return true;
		}

	</script>
</head>
<body>
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
<%--					后台的菜单信息--%>
		<%@include file="/pages/Containspage/Background_menu.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
			</tr>
<%--静态包含显示图书列表--%>
<%@include file="/pages/Containspage/BookManager.jsp"%>
			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_edit.jsp?action=addBook&pageNo=${requestScope.pageObj.pagetTotal}">添加图书</a></td>
			</tr>	
		</table>
<%--<%--%>
<%--	//上一页的页码--%>
<%--	int prePage=pageObj.getPageNo();--%>
<%--	//下一页的页码--%>
<%--	int nextPage=pageObj.getPageNo();--%>
<%--	if(prePage!=1)--%>
<%--		prePage--;--%>
<%--	if (nextPage!=pageObj.getPagetTotal())--%>
<%--		nextPage++;--%>
<%--	//显示当前页码的字符串--%>
<%--	String thispage="【"+pageObj.getPageNo()+"】";--%>

<%--%>--%>



<%--		--%>
<%--		<div id="page_nav">--%>
<%--			<a href="manager/BookServlet?action=page">首页</a>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=prePage%>&pageSize=<%=4%>" class="prePage" >上一页</a>--%>
<%--		<%//动态生成页码--%>
<%--			//总页码小于5的情况--%>
<%--			if(pageObj.getPagetTotal()<=5){--%>
<%--				//用于标识页码的字符串--%>
<%--				String pageview;--%>
<%--				for(int i=1;i<=pageObj.getPagetTotal();i++){--%>
<%--					if (i==pageObj.getPageNo())--%>
<%--						pageview="【"+i+"】";--%>
<%--					else--%>
<%--						pageview=String.valueOf(i);--%>
<%--					%>--%>

<%--		<a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>--%>
<%--		<%--%>
<%--				}--%>
<%--			}--%>

<%--		%>--%>



<%--			<%--%>
<%--				//总页码大于5的情况--%>
<%--				if (pageObj.getPagetTotal()>5){--%>
<%--					//用于标识页码的字符串--%>
<%--					String pageview;--%>
<%--					//当页码是前面三个的情况,范围为1-5--%>
<%--					if (pageObj.getPageNo()>=1&&pageObj.getPageNo()<=3){--%>
<%--						for (int i=1;i<=5;i++){--%>
<%--							if (i==pageObj.getPageNo())--%>
<%--								pageview="【"+i+"】";--%>
<%--							else pageview=String.valueOf(i);--%>
<%--							//生成页码--%>
<%--							%>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>--%>
<%--			<%--%>
<%--						}--%>
<%--					}--%>

<%--					if (pageObj.getPageNo()>=pageObj.getPagetTotal()-2&&pageObj.getPageNo()<=pageObj.getPagetTotal()){--%>
<%--						//最后三个--%>
<%--						for (int i=pageObj.getPagetTotal()-4;i<=pageObj.getPagetTotal();i++){--%>
<%--							if (i==pageObj.getPageNo())--%>
<%--								pageview="【"+i+"】";--%>
<%--							else pageview=String.valueOf(i);%>--%>
<%--							<a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>--%>
<%--			<%--%>
<%--						}--%>
<%--					}--%>
<%--					if (pageObj.getPageNo()>=4&&pageObj.getPageNo()<=pageObj.getPagetTotal()-3){--%>
<%--						for (int i=pageObj.getPageNo()-2;i<=pageObj.getPageNo()+2;i++){--%>
<%--							if (i==pageObj.getPageNo())--%>
<%--								pageview="【"+i+"】";--%>
<%--							else pageview=String.valueOf(i);--%>
<%--							%>--%>
<%--<a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>--%>
<%--			<%--%>
<%--						}--%>
<%--					}--%>

<%--				}--%>
<%--			%>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=nextPage%>&pageSize=<%=4%>" class="nextPage">下一页</a>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=pageObj.getPagetTotal()%>&pageSize=<%=4%>">末页</a>--%>
<%--			共<%=pageObj.getPagetTotal()%>页 ${requestScope.allCount.toString()}条记录 到第<input value="<%=pageObj.getPageNo()%>" name="pn" class="kk" id="pn_input" type="text"/>页--%>
<%--			<input class="selectpage" type="button" value="确定">--%>
<%--		</div>--%>


<%@include file="/pages/Containspage/getNewPages.jsp"%>


<%--		<div id="page_nav">--%>
<%--			<a href="manager/BookServlet?action=page">首页</a>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=prePage%>&pageSize=<%=4%>" class="prePage" >上一页</a>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=prePage%>&pageSize=<%=4%>"><%=prePage%></a>--%>
<%--			<%=thispage%>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=nextPage%>&pageSize=<%=4%>"><%=nextPage%></a>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=nextPage%>&pageSize=<%=4%>" class="nextPage">下一页</a>--%>
<%--			<a href="manager/BookServlet?action=page&pageNo=<%=pageObj.getPagetTotal()%>&pageSize=<%=4%>">末页</a>--%>
<%--			共<%=pageObj.getPagetTotal()%>页 ${requestScope.allCount.toString()}条记录 到第<input value="<%=pageObj.getPageNo()%>" name="pn" class="kk" id="pn_input" type="text"/>页--%>
<%--			<input class="selectpage" type="button" value="确定">--%>
<%--		</div>--%>
	</div>

	<%--	页脚信息--%>
	<%@include file="/pages/Containspage/Footage.jsp"%>
</body>
</html>