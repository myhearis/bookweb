<%@ page import="my.pojo.Page" %>
<%@ page import="my.pojo.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--前台页码展示--%>
<%

    //上一页的页码
    int prePage=pageObj.getPageNo();
    //下一页的页码
    int nextPage=pageObj.getPageNo();
    if(prePage!=1)
        prePage--;
    if (nextPage!=pageObj.getPagetTotal())
        nextPage++;
    //显示当前页码的字符串
    String thispage="【"+pageObj.getPageNo()+"】";

    int start=0,end=0;
    //总页码小于5的情况
    if (pageObj.getPagetTotal()<=5){
        start=1;
        end=5;
    }
    //大于5的情况
    else{
        //情况1：当前页码在1-3
        if (pageObj.getPageNo()>=1&&pageObj.getPageNo()<=3){
            start=1;
            end=5;
        }
        //在第四个到倒数第三个之间
        else if (pageObj.getPageNo()>=4&&pageObj.getPageNo()<=pageObj.getPagetTotal()-3){
            start=pageObj.getPageNo()-2;
            end=pageObj.getPageNo()+2;
        }
        else if (pageObj.getPageNo()>=pageObj.getPagetTotal()-2&&pageObj.getPageNo()<=pageObj.getPagetTotal()){
            start=pageObj.getPagetTotal()-4;
            end=pageObj.getPagetTotal();
        }
    }


%>
<div id="page_nav">
    <a href="Client/ClientServlet?action=page">首页</a>
    <a href="Client/ClientServlet?action=page&pageNo=<%=prePage%>&pageSize=<%=4%>" class="prePage" >上一页</a>
    <%//动态生成页码
        //用于标识页码的字符串
        String pageview;
        for(int i=start;i<=end;i++){
            if (i==pageObj.getPageNo())
                pageview="【"+i+"】";
            else
                pageview=String.valueOf(i);
    %>

    <a href="Client/ClientServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>
    <%
        }


    %>

    <a href="Client/ClientServlet?action=page&pageNo=<%=nextPage%>&pageSize=<%=4%>" class="nextPage">下一页</a>
    <a href="Client/ClientServlet?action=page&pageNo=<%=pageObj.getPagetTotal()%>&pageSize=<%=4%>">末页</a>
    共<%=pageObj.getPagetTotal()%>页 ${requestScope.allCount.toString()}条记录 到第<input value="<%=pageObj.getPageNo()%>" name="pn" class="kk" id="pn_input" type="text"/>页
    <input class="selectpage" type="button" value="确定">
</div>

