package my.dao_impl;

import my.pojo.Order;
import my.pojo.OrderDao;
import my.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname OrderImpl
 * @author: 我心
 * @Description:订单的Dao实现类
 * @Date 2021/10/30 23:40
 * @Created by Lenovo
 */
public class OrderImpl extends BaseDao implements OrderDao {
    //保存订单到数据库
    @Override
    public int saveOrder(Connection connection, Order order) throws SQLException {
        String sql="INSERT INTO `order`(orderId,username,creaeTime,price,`status`) VALUES(?,?,?,?,?)";
        int i = updateTransaction(connection, sql, order.getOrderId(),order.getUsername(),order.getCreaeTime(),order.getPrice(),order.getStatus());
        return i;

    }
    //返回所有的订单

    @Override
    public List<Order> queryOrders(Connection connection) throws SQLException {
        String sql="SELECT * FROM `order`;";
        List<Order> orders = queryForListTransaction(Order.class, connection, sql);
        return orders;

    }
//    返回对应的分页订单信息
    @Override
    public List<Order> getOrderPages(Connection connection, int begin, int pagesize) throws SQLException {
        String sql="SELECT * FROM `order` LIMIT ?,?";
        List<Order> orders = queryForListTransaction(Order.class, connection, sql, begin, pagesize);
        return orders;

    }

    //修改状态信息
    @Override
    public int changeOrderStatus(Connection connection, String orderId, int status) throws SQLException {
        String sql="UPDATE `order` SET status=? WHERE orderId=?";
        int i = updateTransaction(connection, sql, status, orderId);
        return i;

    }

    //显示用户所有订单
    @Override
    public List<Order> showUserOrders(Connection connection, String username) throws SQLException {
        String sql="SELECT orderId,creaeTime,price,status,username  FROM  `order` WHERE username=?;";
        List<Order> orders = queryForListTransaction(Order.class, connection, sql, username);
        return orders;

    }
    //获取总记录数
    @Override
    public int getOrderAllCount(Connection connection) throws SQLException {
        String sql="SELECT COUNT(*) FROM `order`";
        Object value = getValue(connection, sql);
        int i = Integer.parseInt(String.valueOf((Long) value));
        return i;

    }

    public static void main(String[] args) {
        OrderImpl order = new OrderImpl();
        Connection connection=null;
        try {
             connection= JDBCUtils.getConnection();
//            Date date = new Date(new java.util.Date().getTime());
//            Order order1 = new Order(date,31234,-1,"fwefwe");
//            int i = order.saveOrder(connection, order1);
//            System.out.println(i);
//            List<Order> orderPages = order.getOrderPages(connection, 0, 4);
//            System.out.println(orderPages);
//            System.out.println(order.getOrderAllCount(connection));
            System.out.println(order.getOrderPages(connection, 0, 4));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
