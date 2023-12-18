package my.utils;

import my.pojo.Goods;
import my.pojo.OrderItem;
import my.pojo.ShoppingCart;
import my.pojo.User;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Classname OrderUtils
 * @author: 我心
 * @Description:订单模块工具类
 * @Date 2021/10/31 10:41
 * @Created by Lenovo
 */
public class OrderUtils {
    //生成唯一的订单号:用户名转化的字节数组+当前时间戳
    public static String createOrderId(User user){
        StringBuffer stringBuffer=new StringBuffer();
//        byte[] bytes = user.getUsername().getBytes(StandardCharsets.UTF_8);
//        for (byte aByte : bytes) {
//            stringBuffer.append(aByte);
//        }
        stringBuffer.append(user.getUsername());
        stringBuffer.append(new Date().getTime());
        return stringBuffer.toString();
    }
    public static String createOrderId(String username,Date createTime){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(username);
        stringBuffer.append(createTime.getTime());
        return stringBuffer.toString();
    }
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("hello");
        System.out.println(createOrderId(user));
    }
    //将购物车中的goods对象转化为订单项的集合
    public static List<OrderItem> ShoppingCartGoodsGetOrder(ShoppingCart shoppingCart,String orderId){
        List<OrderItem> list=new ArrayList<>();
        HashMap<Integer, Goods> goodsMap = shoppingCart.getGoodsMap();
        for (Goods value : goodsMap.values()) {
            OrderItem orderItem=new OrderItem(value.getName(), value.getCount(), value.getPrice(), value.getTotalPrice(), orderId);
            //设置当前项的商品编号
            orderItem.setGoodId(value.getId());
            list.add(orderItem);
        }
        return list;
    }
}
