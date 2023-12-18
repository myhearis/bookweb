package my.pojo;

/**
 * @Classname OederItem
 * @author: 我心
 * @Description:订单项:显示一个订单详情的一个商品信息
 * @Date 2021/10/30 22:59
 * @Created by Lenovo
 */
public class OrderItem {
    private int goodId;//当前项的商品编号
    private int id;//记录编号
    private String name;//商品名称
    private int count;//商品数量
    private double price;//单价
    private double totalPrice;//商品总价
    private String oderId;//订单编号

    public OrderItem() {
    }

    public OrderItem(int id, String name, int count, double price, double totalPrice, String oderId) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.oderId = oderId;
    }
    public OrderItem(String name, int count, double price, double totalPrice, String oderId) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.oderId = oderId;
    }
    public int getId() {
        return id;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
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

    public String getOderId() {
        return oderId;
    }

    public void setOderId(String oderId) {
        this.oderId = oderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", oderId='" + oderId + '\'' +
                '}';
    }
}
