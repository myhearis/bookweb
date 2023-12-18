package my.service_impl;

import my.pojo.Book;
import my.pojo.Page;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * @Classname BookService
 * @author: 我心
 * @Description:
 * @Date 2021/10/16 20:23
 * @Created by Lenovo
 */
public interface BookService {
    //添加图书
    boolean addBook( Book book);
    //删除图书
    boolean deleteBookById(int id);
    //更新图书
    boolean updateBook(Book book);
    //查找图书
    Book queryBookById(int id) ;
    //返回所有图书的集合
    public List<Book> queryBooks();

//    返回价格区间的图书集合
    List<Book> getPriceRange(BigDecimal minPrice,BigDecimal maxPrice);
    //返回当前的记录数
    public Long queryForPageTotalCount();
    //返回分页区间的数据
    public List<Book> queryForItems(int begin,int pageSize);
    //处理分页，封装一个分页对象
    public Page page(int pageNo,int pageSize);
    //返回一个区间分页对象
    public Page<Book> pageRange(int minPrice,int maxPrice,int pageNo,int pageSize);
}
