package my.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname OrderDao
 * @author: 我心
 * @Description:
 * @Date 2021/10/30 23:34
 * @Created by Lenovo
 */
public interface OrderDao {
    //保存订单
    int saveOrder(Connection connection,Order order) throws SQLException;
    //查询全部订单(管理员用)
    List<Order> queryOrders(Connection connection) throws SQLException;
    //返回订单分页信息
    List<Order> getOrderPages(Connection connection,int begin,int pagesize) throws SQLException;
    //修改订单状态
    int changeOrderStatus(Connection connection,String orderId,int status) throws SQLException;
    //显示传入用户的所有订单
    List<Order> showUserOrders(Connection connection,String username) throws SQLException;
    //获取总订单数
    int getOrderAllCount(Connection connection) throws SQLException;
}
