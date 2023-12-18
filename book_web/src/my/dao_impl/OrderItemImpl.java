package my.dao_impl;

import my.pojo.OrderItem;
import my.pojo.OrderItemDao;
import my.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname OrderItemImpl
 * @author: 我心
 * @Description:
 * @Date 2021/10/31 0:42
 * @Created by Lenovo
 */
public class OrderItemImpl extends BaseDao implements OrderItemDao {
    //将对应的订单项保存到数据库中
    @Override
    public int saveOrderItem(Connection connection, OrderItem orderItem) throws SQLException {
        String sql="INSERT INTO orderitem(name,count,price,totalPrice,oderId) VALUES(?,?,?,?,?);";
        int i = updateTransaction(connection,sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOderId());
        return i;

    }
//通过订单号查询用户的订单,返回一个列表
    @Override
    public List<OrderItem> queryOrderByOrderId(Connection connection, String orderId) throws SQLException {
        String sql="SELECT * FROM orderitem WHERE oderId=?";
        List<OrderItem> orderItems = queryForListTransaction(OrderItem.class, connection, sql, orderId);
        return orderItems;

    }

    public static void main(String[] args)  {
        OrderItemImpl orderItem = new OrderItemImpl();
        try {
            Connection connection= JDBCUtils.getConnection();
            OrderItem orderItem1 = new OrderItem("fdsf", 3, 3542, 4535, "fffff");
            System.out.println(orderItem1);
            int i = orderItem.saveOrderItem(connection, orderItem1);
            System.out.println(i);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
