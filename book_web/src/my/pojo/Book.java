package my.pojo;

import java.math.BigDecimal;

/**
 * @Classname Book
 * @author: 我心
 * @Description:book类，与数据库中t_book表相对应
 * @Date 2021/10/16 16:41
 * @Created by Lenovo
 */
public class Book {
    private int id;
    private  String name;
    private BigDecimal price;
    private String author;//作者
    private int sales;//销量
    private int stock;//库存
    private String imgPath="static/img/dlam.jpg";//图片位置

    public Book() {

    }

    public Book(int id, String name, BigDecimal price, String author, int sales, int stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.sales = sales;
        this.stock = stock;
        //路径不为空，且长度不为0时可以赋值
        if (imgPath!=null&&imgPath.length()!=0)
            this.imgPath = imgPath;
    }

    public Book(String name, BigDecimal price, String author, int sales, int stock, String imgPath) {
        this.name = name;
        this.price = price;
        this.author = author;
        this.sales = sales;
        this.stock = stock;
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", sales=" + sales +
                ", stock=" + stock +
                ", imgPath='" + imgPath + '\'' +
                '}';
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        //路径不为空，且长度不为0时可以赋值
        if (imgPath!=null&&imgPath.length()!=0)
            this.imgPath = imgPath;
    }
}
