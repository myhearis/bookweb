package my.pojo;


import my.utils.OrderUtils;

import java.sql.Date;

/**
 * @Classname Order
 * @author: 我心
 * @Description:订单类
 * @Date 2021/10/30 23:15
 * @Created by Lenovo
 */
public class Order {
    public static final int NOT_SHIP=-1;//未发货
    public static final int SHIPPED=0;//已发货
    public static final int RECEIVED=1;//已签收
    private String orderId;//订单编号
    private Date creaeTime;//下单时间
    private double price;//金额
    private int status=NOT_SHIP;//状态(默认未发货)
    private String username;//用户名

    //根据传入的下单时间和用户名初始化订单编号
    public void initOrderId(){
        this.orderId= OrderUtils.createOrderId(this.username,creaeTime);
    }

    public Order(String orderId, Date creaeTime, double price, int status, String username) {
        this.orderId = orderId;
        this.creaeTime = creaeTime;
        this.price = price;
        this.status = status;
        this.username = username;
        initOrderId();
    }

    public Order(Date creaeTime, double price, int status, String username) {
        this.creaeTime = creaeTime;
        this.price = price;
        this.status = status;
        this.username = username;
        initOrderId();
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreaeTime() {
        return creaeTime;
    }

    public void setCreaeTime(Date creaeTime) {
        this.creaeTime = creaeTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//返回状态的字符串
    public String getStatusString(){
        String s=null;
        if (this.status==NOT_SHIP)
            s="未发货";
        else if (this.status==RECEIVED)
            s="已收货";
        else if (this.status==SHIPPED)
            s="已发货";
        return s;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", creaeTime=" + creaeTime +
                ", price=" + price +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }
}
