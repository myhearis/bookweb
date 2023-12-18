<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="my.pojo.User" %>
<%@ page import="my.pojo.ShoppingCart" %>
<%@ page import="my.pojo.Goods" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="com.mysql.cj.x.protobuf.MysqlxDatatypes" %><%
    User user = (User) session.getAttribute("user");
    ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute(user.getUsername() + ":shoppingCart");
    Collection<Goods> values = shoppingCart.getGoodsMap().values();

%>
<%
    for (Goods value : values) {%>
    <tr>
    <td><%=value.getName()%></td>
    <td>2</td>
    <td><%=value.getPrice()%></td>
    <td><%=value.getTotalPrice()%></td>
    <td><a href="#" ><%="删除"%></a></td>
    </tr>
<%
    }
%>
