package my.dao_impl;

import my.pojo.Book;
import my.pojo.BookDao;
import my.utils.JDBCUtils;
import org.hamcrest.Condition;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Classname BookImpl
 * @author: 我心
 * @Description:
 * @Date 2021/10/16 19:02
 * @Created by Lenovo
 */
public class BookImpl extends BaseDao implements BookDao {
//    添加图书信息
    @Override
    public int addBook(Connection connection, Book book)  {
       String sql="INSERT INTO t_book(name,author,price,sales,stock,img_path) VALUES(?,?,?,?,?,?);";
        int i = 0;
        try {
            i = updateTransaction(connection, sql, book.getName(), book.getAuthor(), book.getPrice(),
                    book.getSales(), book.getStock(), book.getImgPath());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return i;
    }
//删除图书
    @Override
    public int deleteBookById(Connection connection,int id)  {
        String sql="DELETE FROM t_book WHERE id=?;";
        int i = 0;
        try {
            i = updateTransaction(connection, sql, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return i;
    }
//更新图书信息
    @Override
    public int updateBook(Connection connection,Book book) {
        String sql="UPDATE T_book SET NAME=?,author=?,price=?,sales=?,stock=?,img_path=? WHERE id=?";
        try {
            int i = 0;
            i = updateTransaction(connection, sql, book.getName(), book.getAuthor(), book.getPrice(),
                    book.getSales(), book.getStock(), book.getImgPath(),book.getId());

            return i;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
//通过id查找图书,找不到返回null
    @Override
    public Book queryBookById(Connection connection,int id)  {
        String sql="SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book WHERE id=?;";
        Book book = null;
        try {
            book = queryForOneTransaction(Book.class, connection, sql, id);
            return book;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return book;
    }

    @Override
    public List<Book> queryBooks(Connection connection) {
        String sql="SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book;";
        List<Book> books = null;
        try {
            books = queryForListTransaction(Book.class, connection, sql);
            return books;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }
//返回价格区间内的书集合
    @Override
    public List<Book> getPriceRange(Connection connection, BigDecimal minPrice,BigDecimal maxPrice)  {
        String sql="SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book WHERE price>=? AND price<=?;";
        try {
            return queryForListTransaction(Book.class,connection, sql, minPrice,maxPrice);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
//返回总记录数
    @Override
    public Long queryForPageTotalCount(Connection connection) {
        String sql="SELECT COUNT(*) FROM t_book;";
        try {
            Object value = getValue(connection, sql);
            if(value instanceof Long)
                return (Long) value;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Long("-1");
    }
//返回分页数据
    @Override
    public List<Book> queryForItems(Connection connection, int begin, int pageSize)  {
        String sql="SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book LIMIT ?,?;";
        try {
            List<Book> list = queryForListTransaction(Book.class, connection, sql, begin, pageSize);
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    //返回价格区间对应分页的图书集合
    @Override
    public List<Book> getPagePriceRangeList(Connection connection, BigDecimal minPrice, BigDecimal maxPrice, int pageNo, int pageSize)  {
        String sql="SELECT a.id,name,a.author,a.price,a.sales,a.stock,a.imgPath FROM (SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book WHERE price>=? AND price<=?) a LIMIT ?,?;";
        try {
            List<Book> list = queryForListTransaction(Book.class, connection, sql, minPrice, maxPrice, pageNo, pageSize);
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    //返回分页价格区间的所有记录数目
    @Override
    public int getPriceRangeCount(Connection connection, int minPrice, int maxPrice, int begin, int pageSize)  {
        String sql="SELECT count(*) count FROM (SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book WHERE price>=? AND price<=?) a;";
        try {
            int count=0;
            Long value = (Long) getValue(connection, sql, minPrice, maxPrice);
            count=Integer.parseInt(String.valueOf(value));
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int newGetCount(Connection connection, int minPrice, int maxPrice)  {
        String sql="SELECT count(*) count FROM (SELECT id,name,author,price,sales,stock,img_path imgPath FROM t_book WHERE price>=? AND price<=?) a;";
        int count=-1;
        try {
            Long value = (Long) getValue(connection, sql, minPrice, maxPrice);
            count=Integer.parseInt(String.valueOf(value));
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;

    }


    public static void main(String[] args) throws SQLException {
        BookImpl book = new BookImpl();
        Connection connection = JDBCUtils.getConnection();
//        //删除21号图书
//        int i = book.deleteBookById(connection, 21);
//        System.out.println(i);
//        List<Book> books = book.getPriceRange(connection,new BigDecimal(20),new BigDecimal(50));
//        for (Book book1 : books) {
//            System.out.println(book1);
////        }
//        System.out.println(book.queryForItems(connection, 0, 4));
//        //关闭连接
//        System.out.println(book.queryForPageTotalCount(connection));
//        List<Book> pagePriceRangeList = book.getPagePriceRangeList(connection, new BigDecimal(50), new BigDecimal(100), 0, 9);
//        pagePriceRangeList.forEach(new Consumer<Book>() {
//            @Override
//            public void accept(Book book) {
//                System.out.println(book);
//            }
//        });
        System.out.println(book.getPriceRangeCount(connection, 50, 100, 0, 9));
        connection.close();
    }
}
