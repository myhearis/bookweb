package my.pojo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname BookDao
 * @author: 我心
 * @Description:
 * @Date 2021/10/16 17:25
 * @Created by Lenovo
 */
public interface BookDao {
    //添加图书
    int addBook(Connection connection, Book book) throws SQLException;
//删除图书
    int deleteBookById(Connection connection,int id) throws SQLException;
//更新图书
    int updateBook(Connection connection,Book book) throws SQLException;
//查找图书
    Book queryBookById(Connection connection,int id) throws SQLException;
//返回所有图书的集合
    public List<Book> queryBooks(Connection connection) throws SQLException;
//返回对应价格区间的集合
    List<Book> getPriceRange(Connection connection, BigDecimal minPrice,BigDecimal maxPrice) throws SQLException;
    //返回当前的记录数
    public Long queryForPageTotalCount(Connection connection) throws SQLException;
    //返回分页区间的数据
    public List<Book> queryForItems(Connection connection,int begin,int pageSize) throws SQLException;
    //返回价格区间的分页集合
    List<Book> getPagePriceRangeList(Connection connection,BigDecimal minPrice,BigDecimal maxPrice,int pageNo,int pageSize) throws SQLException;
    //返回价格区间分页的所有记录的数目
    int getPriceRangeCount(Connection connection,int minPrice,int maxPrice,int begin,int pageSize) throws SQLException;
    //优化过的返回价格区间所有记录数目的方法
    int newGetCount(Connection connection,int minPrice,int maxPrice) throws SQLException;
}
