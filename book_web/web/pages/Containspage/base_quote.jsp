<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2021/10/14
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--动态获取base标签值--%>
<%
    String baseStr=request.getScheme()+"://"+
            request.getServerName()+":"+request.getServerPort()
            +request.getContextPath()+"/";
    //将当前的base的值放到整个web域中
    pageContext.setAttribute("basePath",baseStr);
%>
<%--引用与base标签--%>

<base href="<%=baseStr%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >

