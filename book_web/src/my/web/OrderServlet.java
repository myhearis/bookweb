package my.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.dao_impl.BaseDao;
import my.pojo.*;
import my.service_impl.OrderService;
import my.service_impl.OrderServiceImpl;
import my.utils.JDBCUtils;
import my.utils.OrderUtils;
import my.utils.ShoppingUtils;
import my.utils.WebUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Classname OrderServlet
 * @author: 我心
 * @Description:
 * @Date 2021/10/31 10:39
 * @Created by Lenovo
 */
public class OrderServlet extends BaseServlet<OrderServlet> {
    OrderService orderService=new OrderServiceImpl();
    //返回订单分页页面
    public void OrderPages(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取要显示的页码，大小
        System.out.println("获取到了分页方法");
        String pageNo = req.getParameter("pageNo");
        String pageSize = req.getParameter("pageSize");
        Page<Order> orderPages = orderService.getOrderPages(WebUtils.PassInt(pageNo, 1), WebUtils.PassInt(pageSize, 4));
        req.setAttribute("pageurl","OrderServlet?action=OrderPages");
        req.setAttribute("pageObj",orderPages);
        req.setAttribute("action","OrderPages");
        req.setAttribute("allCount",orderService.getCountOrders());
        req.getRequestDispatcher("/pages/order/order_manager2.jsp").forward(req,resp);
    }
    //管理员发货
    public void send(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderId = req.getParameter("orderId");
        //发货
        boolean b = orderService.sendOrder(orderId);
        if (b){
            //发货成功
            //重定向重新进入订单界面
            //获取当前是第几页
            String pageNo=req.getParameter("pageNo");
            String pageSize=req.getParameter("pageSize");
            resp.sendRedirect(req.getContextPath()+"/OrderServlet?action=OrderPages&pageNo="+pageNo+"&pageSize="+pageSize);
        }
    }
    //显示所有订单
    public void showOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取所有的订单信息
        List<Order> orders = orderService.queryOrders();
        //添加到请求域中
        req.setAttribute("orders",orders);
        //请求转发
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req,resp);
    }
    //确认收货
    public void receive(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderId = req.getParameter("orderId");
        boolean b = orderService.receiveOrder(orderId);
        if (b){
            //收货成功后请求转发请求重定向回到订单界面
            resp.sendRedirect(req.getContextPath()+"/OrderServlet?action=getMyOrders");
        }
    }
    //查看订单详情
    public void showOrderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        System.out.println(orderId);
        //获取对应订单编号的商品项的集合
        List<OrderItem> orderItems = orderService.showOrderDetail(orderId);
        //保存到请求域中
        req.setAttribute("orderItemList",orderItems);
        //请求转发
        req.getRequestDispatcher("/pages/order/OrderItem.jsp").forward(req,resp);
    }
    //查看当前用户订单
    public void getMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取当前用户
        User thisUser = ShoppingUtils.getThisUser(req.getSession());
        List<Order> orders = orderService.showMyOrder(thisUser.getUsername());
        //请求转发给订单页面
        req.setAttribute("orders",orders);
        req.getRequestDispatcher("/pages/order/order.jsp").forward(req,resp);
    }
    //生成订单
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("结账方法");
        //获取购物车对象
        ShoppingCart userShoppingCart = ShoppingUtils.getUserShoppingCart(req.getSession());
        //如果购物车没有商品，则重定向回购物车
        if (userShoppingCart.getTotalCount()==0){
            try {
                resp.sendRedirect(req.getContextPath()+"/pages/cart/cart.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //生成订单
        Order order = orderService.createOrder(userShoppingCart, ShoppingUtils.getThisUser(req.getSession()).getUsername());
        System.out.println(order);
        List<OrderItem> orderItemlist = OrderUtils.ShoppingCartGoodsGetOrder(userShoppingCart, order.getOrderId());
        //将购物车中的商品转化为订单项,并保存到数据库
             boolean b = false;
            b = orderService.saveOrder(order,orderItemlist);
            JDBCUtils.commitAndClose();//提交事务
        if (b){
            //写入数据库成功后，清空购物车
            userShoppingCart.clear();
            //重定向回订单成功页面
            try {
                resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp?orderId="+order.getOrderId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //重定向返回购物车界面
            try {
                resp.sendRedirect(req.getContextPath()+"/pages/cart/cart.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
