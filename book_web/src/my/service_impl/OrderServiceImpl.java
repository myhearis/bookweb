package my.service_impl;

import my.dao_impl.OrderImpl;
import my.dao_impl.OrderItemImpl;
import my.pojo.*;
import my.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Classname OrderServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2021/10/31 9:11
 * @Created by Lenovo
 */
public class OrderServiceImpl implements OrderService{
    //创建订单dao实现类和订单项dao实现类
    OrderDao orderDao=new OrderImpl();
    OrderItemDao orderItemDao=new OrderItemImpl();
    BookService bookService=new BookServiceImpl();
    //保存订单
    @Override
    public boolean saveOrder(Order order) throws SQLException {
        Connection connection=null;
        connection= JDBCUtils.getConnectionAuto();
        int i = orderDao.saveOrder(connection, order);
        if (i>0)
            return true;
        return false;

    }

//查询所有订单
    @Override
    public List<Order> queryOrders() {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            List<Order> orders = orderDao.queryOrders(connection);
            return orders;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }   finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

//修改订单状态
    @Override
    public boolean changeOrderStatus(String orderId, int status) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            int i = orderDao.changeOrderStatus(connection, orderId, status);
            if (i>0)
                return true;
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return false;
    }
//管理员指定对应订单发货
    @Override
    public boolean sendOrder(String orderId) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            int i = orderDao.changeOrderStatus(connection, orderId, Order.SHIPPED);
            if (i>0)
                return true;
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return false;
    }
//显示订单详情
    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            List<OrderItem> orderItems = orderItemDao.queryOrderByOrderId(connection, orderId);
            return orderItems;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }
//显示当前用户的所有订单
    @Override
    public List<Order> showMyOrder(String username) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            List<Order> orders = orderDao.showUserOrders(connection, username);
            return orders;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }   finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }
//用户确认收货
    @Override
    public boolean receiveOrder(String orderId) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            int i = orderDao.changeOrderStatus(connection, orderId, Order.RECEIVED);
            if (i>0)
                return true;
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return false;
    }
//保存订单，同时将传入对应的订单项集合保存到数据库
    @Override
    public boolean saveOrder(Order order, List<OrderItem> orderItemList) throws Exception {
        Connection connection=null;

            connection=JDBCUtils.getConnectionAuto();
            connection.setAutoCommit(false);//设置非自动提交
            //先保存订单到数据库
            int i = orderDao.saveOrder(connection, order);
            if (i>0){
                //保存订单项
                for (OrderItem orderItem : orderItemList) {
                    boolean i1 = saveOrderItem( orderItem);

                }
                return true;
            }


        return false;
    }

    @Override
    public boolean saveOrderItem(OrderItem orderItem) throws Exception{
             Connection connection=null;

            connection=JDBCUtils.getConnectionAuto();//获取线程数据库连接
           // connection.setAutoCommit(false);//不自动提交
            int i = orderItemDao.saveOrderItem(connection, orderItem);//保存一个订单项到数据库
            Book book = bookService.queryBookById(orderItem.getGoodId());//获取对应的商品对象

            int stock = book.getStock() - orderItem.getCount();//获取更新后的库存
            int sale=book.getSales()+orderItem.getCount();//获取销量
            if(stock>0){
                book.setStock(stock);//更新这个商品的库存
                //更新销量
                book.setSales(sale);
                //写入数据库更新信息
                boolean b = bookService.updateBook(book);
                System.out.println("信息状态为"+b);
                if (i>0)
                    return true;
            }
            else
                throw new Exception("商品编号"+orderItem.getGoodId()+"库存不足");

            return false;

    }
    //通过传入当前用户的购物车对象和当前用户名生成订单
    @Override
    public Order createOrder(ShoppingCart shoppingCart, String username) {
        Date date = new Date(new java.util.Date().getTime());
        Order order=new Order(date,shoppingCart.getTotalPrice(),Order.NOT_SHIP,username);
        return order;
    }
    //返回订单分页信息（已写完）
    @Override
    public Page<Order> getOrderPages(int pageNo, int pageSize) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            //创建分页对象
            Page<Order> orderPage = new Page<>();
            //获取总记录数
            int orderAllCount = orderDao.getOrderAllCount(connection);
            orderPage.setAllCount(orderAllCount);//设置总记录数
            //求总页码，先设置总页码，才能设置当前页码
            int allPageNo=orderAllCount/pageSize;
            if(orderAllCount%pageSize!=0)
                allPageNo++;
            //设置总页码
            orderPage.setPagetTotal(allPageNo);
            //设置当前页码
            orderPage.setPageNo(pageNo);
            //当前页的首索引
            int start=(orderPage.getPageNo()-1)*pageSize;
            //查询分页内容
            List<Order> orderPagesList = orderDao.getOrderPages(connection, start, pageSize);
            //添加分页内容
            orderPage.setList(orderPagesList);
            return orderPage;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }
//返回订单的总数目
    @Override
    public int getCountOrders() {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            return orderDao.getOrderAllCount(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        OrderService orderService = new OrderServiceImpl();
//        List<Order> helloworld = orderService.showMyOrder("helloworld");
//        helloworld.forEach(new Consumer<Order>() {
//            @Override
//            public void accept(Order order) {
//                System.out.println(order);
//            }
//        });
//        //修改订单状态
//        System.out.println(orderService.sendOrder("helloworld10.31"));
//        //收货
//        System.out.println(orderService.receiveOrder("helloworld10.31"));
//        //显示订单详情
//        List<OrderItem> orderItems = orderService.showOrderDetail("helloworld10.31");
//        orderItems.forEach(new Consumer<OrderItem>() {
//            @Override
//            public void accept(OrderItem orderItem) {
//                System.out.println(orderItem);
//            }
//        });
//        //保存一个订单项
//        OrderItem orderItem=new OrderItem(2,"数据结构与算法",2, 78.50, 157, "helloworld10.31");
//        System.out.println(orderService.saveOrderItem(orderItem));
        Page<Order> orderPages = orderService.getOrderPages(0, 4);
        System.out.println(orderPages);
        System.out.println(orderService.getCountOrders());
    }
}
