<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

%>
<div id="page_nav">
    <a href="manager/BookServlet?action=page">首页</a>
    <a href="manager/BookServlet?action=page&pageNo=<%=prePage%>&pageSize=<%=4%>" class="prePage" >上一页</a>
    <%//动态生成页码
        //总页码小于5的情况
        if(pageObj.getPagetTotal()<=5){
            //用于标识页码的字符串
            String pageview;
            for(int i=1;i<=pageObj.getPagetTotal();i++){
                if (i==pageObj.getPageNo())
                    pageview="【"+i+"】";
                else
                    pageview=String.valueOf(i);
    %>

    <a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>
    <%
            }
        }

    %>



    <%
        //总页码大于5的情况
        if (pageObj.getPagetTotal()>5){
            //用于标识页码的字符串
            String pageview;
            //当页码是前面三个的情况,范围为1-5
            if (pageObj.getPageNo()>=1&&pageObj.getPageNo()<=3){
                for (int i=1;i<=5;i++){
                    if (i==pageObj.getPageNo())
                        pageview="【"+i+"】";
                    else pageview=String.valueOf(i);
                    //生成页码
    %>
    <a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>
    <%
            }
        }

        if (pageObj.getPageNo()>=pageObj.getPagetTotal()-2&&pageObj.getPageNo()<=pageObj.getPagetTotal()){
            //最后三个
            for (int i=pageObj.getPagetTotal()-4;i<=pageObj.getPagetTotal();i++){
                if (i==pageObj.getPageNo())
                    pageview="【"+i+"】";
                else pageview=String.valueOf(i);%>
    <a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>
    <%
            }
        }
        if (pageObj.getPageNo()>=4&&pageObj.getPageNo()<=pageObj.getPagetTotal()-3){
            for (int i=pageObj.getPageNo()-2;i<=pageObj.getPageNo()+2;i++){
                if (i==pageObj.getPageNo())
                    pageview="【"+i+"】";
                else pageview=String.valueOf(i);
    %>
    <a href="manager/BookServlet?action=page&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>
    <%
                }
            }

        }
    %>
    <a href="manager/BookServlet?action=page&pageNo=<%=nextPage%>&pageSize=<%=4%>" class="nextPage">下一页</a>
    <a href="manager/BookServlet?action=page&pageNo=<%=pageObj.getPagetTotal()%>&pageSize=<%=4%>">末页</a>
    共<%=pageObj.getPagetTotal()%>页 ${requestScope.allCount.toString()}条记录 到第<input value="<%=pageObj.getPageNo()%>" name="pn" class="kk" id="pn_input" type="text"/>页
    <input class="selectpage" type="button" value="确定">
</div>
