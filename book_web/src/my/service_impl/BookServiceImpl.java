package my.service_impl;

import my.dao_impl.BookImpl;
import my.pojo.Book;
import my.pojo.Page;
import my.utils.JDBCUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Classname BookServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2021/10/16 20:26
 * @Created by Lenovo
 */
public class BookServiceImpl implements BookService{
    private BookImpl bookImpl=new BookImpl();//bookDao的实现类
    @Override
    public boolean addBook(Book book) {
        Connection connection=null;
        //获取数据库连接
        try {
            connection = JDBCUtils.getConnection();
            int i = bookImpl.addBook(connection, book);//写入数据库

            if (i>0)
                return true;
            else
                return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            //关闭数据库连接
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

    @Override
    public boolean deleteBookById(int id) {
        Connection connection =null;
        //获取数据库连接
        try {
            connection = JDBCUtils.getConnection();
            int i = bookImpl.deleteBookById(connection, id);
            if (i>0)
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            //关闭数据库连接
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

    @Override
    public boolean updateBook(Book book) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            int i = bookImpl.updateBook(connection, book);
            if (i>0)
                return true;
            else
                return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            //关闭数据库连接
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

    @Override
    public Book queryBookById(int id) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            Book book = bookImpl.queryBookById(connection, id);
            return book;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            //关闭数据库连接
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

    @Override
    public List<Book> queryBooks() {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            List<Book> books = bookImpl.queryBooks(connection);
            return books;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            //关闭数据库连接
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

    @Override
    public List<Book> getPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            return bookImpl.getPriceRange(connection, minPrice, maxPrice);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭数据库连接
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

    @Override
    public Long queryForPageTotalCount() {
        //创建连接
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            Long aLong = bookImpl.queryForPageTotalCount(connection);
            return aLong;
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
        return new Long("-1");
    }

    @Override
    public List<Book> queryForItems( int begin, int pageSize) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            List<Book> list = bookImpl.queryForItems(connection, begin, pageSize);
            return list;
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
//通过传入的页初始值和每页显示量去数据库中获取数据，并将数据封装成一个page类返回
    @Override
    public Page page(int pageNo, int pageSize) {
        Connection connection=null;
        try {
            //创建Page对象
            Page<Book> page=new Page();
            page.setPageSize(pageSize);//设置每页显示的记录数
            connection=JDBCUtils.getConnection();
            //获取记录数
            Long totalCount = bookImpl.queryForPageTotalCount(connection);
            //获取总页码
            Long pageTotal;
            if (totalCount%pageSize>0)
                pageTotal=totalCount/pageSize+1;
            else
                pageTotal=totalCount/pageSize;

            Integer integer=new Integer(String.valueOf(pageTotal));
            page.setPagetTotal(integer);//设置总页码
            page.setPageNo(pageNo);//设置当前页码，因为要进行当前页码的判断，所以要先设置总页码才能设置当前页码
            //求当前页开始的索引
            int begin=(page.getPageNo()-1)*pageSize;
            //获取对应分页的集合
            List<Book> list = bookImpl.queryForItems(connection, begin, pageSize);
            page.setList(list);//设置对应分页数据的集合
            return page;
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
        return null;
    }
//返回价格区间的分页对象
    @Override
    public Page<Book> pageRange(int minPrice, int maxPrice, int pageNo, int pageSize) {
        Connection connection=null;
        try {
            int totalPage=0;
            connection=JDBCUtils.getConnection();
            Page<Book> page = new Page<>();
            page.setPageSize(pageSize);
            //获取总记录数
            int allCount = bookImpl.newGetCount(connection, minPrice, maxPrice);
            page.setAllCount(allCount);
            //计算总页数
            totalPage=allCount/pageSize;
            if (allCount%pageSize!=0)
                totalPage++;
            //设置总页数，为设置当前页数做判断条件
            page.setPagetTotal(totalPage);
            page.setPageNo(pageNo);
            //获取对应的集合
            List<Book> list = bookImpl.getPagePriceRangeList(connection, new BigDecimal(minPrice), new BigDecimal(maxPrice), page.getPageNo(), pageSize);
            page.setList(list);
            return page;
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

    public static void main(String[] args) {
        BookServiceImpl bookService = new BookServiceImpl();
//        Book book=new Book(22,"平凡的世界",new BigDecimal(80),"路遥",9999999, 999999, null);
//        boolean b = bookService.addBook(book);
//        if (b)
//            System.out.println("添加成功！");

////        System.out.println(bookService.updateBook(book));
//        Book book1 = bookService.queryBookById(22);
//        System.out.println(book1);
//        bookService.queryBooks().forEach(new Consumer<Book>() {
//            @Override
//            public void accept(Book book) {
//                System.out.println(book);
//            }
//        });
//        bookService.queryForItems(0,4).forEach(new Consumer<Book>() {
//            @Override
//            public void accept(Book book) {
//                System.out.println(book);
//            }
//        });
//        Page page = bookService.page(1, 7);
//        page.getList().forEach(new Consumer<Book>() {
//            @Override
//            public void accept(Book book) {
//                System.out.println(book);
//            }
//        });
        Page<Book> bookPage = bookService.pageRange(50, 100, 2, 2);
        bookPage.getList().forEach(new Consumer<Book>() {
            @Override
            public void accept(Book book) {
                System.out.println(book);
            }
        });
//        System.out.println(bookService.queryForPageTotalCount());
    }
}
