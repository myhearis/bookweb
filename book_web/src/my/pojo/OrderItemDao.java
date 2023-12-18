package my.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname OederItemDao
 * @author: 我心
 * @Description:
 * @Date 2021/10/30 23:41
 * @Created by Lenovo
 */
public interface OrderItemDao {
    //保存订单到数据库
    int saveOrderItem(Connection connection,OrderItem orderItem) throws SQLException;
    //根据订单号，返回所有这个订单的订单项
    List<OrderItem> queryOrderByOrderId(Connection connection,String orderId) throws SQLException;
}
