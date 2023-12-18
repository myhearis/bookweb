<%@ page import="java.util.List" %>
<%@ page import="my.pojo.Book" %>
<%@ page import="my.pojo.Page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //获取当前分页对象
    Page<Book> pageObj = (Page<Book>) request.getAttribute("pageObj");
    //输出图书列表到网页
    Object list = pageObj.getList();
    List<Book> list1=(List<Book>) list;
    for (Book book : list1) {
%>
<tr>
    <td><%=book.getName()%></td>
    <td><%=book.getPrice()%></td>
    <td><%=book.getAuthor()%></td>
    <td><%=book.getSales()%></td>
    <td><%=book.getStock()%></td>
<%--    发送请求到servlet程序--%>

    <td><a href="manager/BookServlet?action=getupdate&id=<%=book.getId()%>&pageNo=${requestScope.pageObj.pageNo}">修改</a></td>
<%--    在输出内容的同时，确定好要跳转的内容id，同时传入要删除的书所在的页--%>
    <td><a  name="<%=book.getName()%>" class="deleteid" href="manager/BookServlet?action=delete&id=<%=book.getId()%>&pageNo=${requestScope.pageObj.pageNo}">删除</a></td>
</tr>
<%
    }
%>

