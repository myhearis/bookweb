package my.service_impl;

import my.pojo.Order;
import my.pojo.OrderItem;
import my.pojo.Page;
import my.pojo.ShoppingCart;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname OrderService
 * @author: 我心
 * @Description:
 * @Date 2021/10/31 0:33
 * @Created by Lenovo
 */
public interface OrderService {
    //保存订单
    boolean saveOrder(Order order) throws SQLException;
    //查询全部订单(管理员用)
    List<Order> queryOrders();
    //修改订单状态
    boolean changeOrderStatus(String orderId,int status);
    //指定对应订单发货(传入订单号)
    boolean sendOrder(String orderId);
    //查看订单详情
    List<OrderItem> showOrderDetail(String orderId);
    //查看我的订单
    List<Order> showMyOrder(String username);
    //签收订单(确认收货)
    boolean receiveOrder(String orderId);
    //完整的保存订单，同时保存传入对应订单的订单项到数据库
    boolean saveOrder(Order order,List<OrderItem> orderItemList) throws SQLException, Exception;
    //保存一个订单项到数据库
    boolean saveOrderItem(OrderItem orderItem) throws Exception;
    //传入购物车，生成一个订单
    Order createOrder(ShoppingCart shoppingCart,String username);
    //返回订单分页信息（已完）
    Page<Order> getOrderPages(int pageNo, int pageSize);
    int getCountOrders();
}
