<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--优化后的分页方法--%>
<%--需要传入与一个pageurl来确定跳转的地址--%>
<%

    //上一页的页码
    int prePage=pageObj.getPageNo();
    //下一页的页码
    int nextPage=pageObj.getPageNo();
    //如果当前分页不是第一页，则自减
    if(prePage!=1)
        prePage--;
    //如果当前分页不是最后一页，则自增
    if (nextPage!=pageObj.getPagetTotal())
        nextPage++;
    //显示当前页码的字符串
    String thispage="【"+pageObj.getPageNo()+"】";

    int start=0,end=0;
    //总页码小于5的情况
    if (pageObj.getPagetTotal()<=5){
        start=1;
        end=pageObj.getPagetTotal();
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
    <a href="${requestScope.pageurl}">首页</a>
    <a href="${requestScope.pageurl}&pageNo=<%=prePage%>&pageSize=<%=4%>" class="prePage" >上一页</a>
    <%//动态生成页码
            //用于标识页码的字符串
            String pageview;
            for(int i=start;i<=end;i++){
                if (i==pageObj.getPageNo())
                    pageview="【"+i+"】";
                else
                    pageview=String.valueOf(i);
    %>

    <a href="${requestScope.pageurl}&pageNo=<%=i%>&pageSize=<%=4%>"><%=pageview%></a>
    <%
            }


    %>

    <a href="${requestScope.pageurl}&pageNo=<%=nextPage%>&pageSize=<%=4%>" class="nextPage">下一页</a>
    <a href="${requestScope.pageurl}&pageNo=<%=pageObj.getPagetTotal()%>&pageSize=<%=4%>">末页</a>
    共<%=pageObj.getPagetTotal()%>页 ${requestScope.allCount.toString()}条记录 到第<input value="<%=pageObj.getPageNo()%>" name="pn" class="kk" id="pn_input" type="text"/>页
    <input class="selectpage" type="button" value="确定">
</div>
