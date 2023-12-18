package my.pojo;

import java.util.List;
import java.util.Objects;

/**
 * @Classname Goods
 * @author: 我心
 * @Description:购物车中的商品
 * @Date 2021/10/30 10:52
 * @Created by Lenovo
 */
public class Goods {
    private int id;//商品编号
    private String name;//商品名称
    private int count;//商品数量
    private double price;//单价
    private double totalPrice;//商品总价
    //创建数量为1的商品

    public Goods(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count=1;
        updateTotalPrice();
    }

    //只需要设置商品编号，名称，单价，数量即可初始化所有
    public Goods(int id, String name, int count, double price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        updateTotalPrice();
    }

    public Goods(int id, String name, int count, double price, double totalPrice) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }
//    更新总价
    public void updateTotalPrice(){
        setTotalPrice(this.count*this.price);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        updateTotalPrice();//修改数量的同时更新总价格

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id && count == goods.count && Double.compare(goods.price, price) == 0 && Double.compare(goods.totalPrice, totalPrice) == 0 && Objects.equals(name, goods.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, count, price, totalPrice);
    }

    public Goods() {
    }

}
