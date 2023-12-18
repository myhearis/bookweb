package my.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;


import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Classname JDBCUtils
 * @author: 我心
 * @Description: jdbc工具类
 * @Date 2021/10/1 16:42
 * @Created by Lenovo
 */
public class JDBCUtils {
    //Druid数据库连接池
    private static DataSource druid;
    //线程关联工具类
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
    //静态代码块
    static {
        //初始化数据库连接池
        initializationDruid();
    }
    //初始化druid数据库连接池的方法
    public static void initializationDruid() {
        InputStream inputStream=null;
        //创建配置文件类
        Properties properties = new Properties();
        try {
            //读取src下的配置文件
            inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            //加载
            properties.load(inputStream);
            //工厂类返回一个数据库连接池
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //赋值
            druid=dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //关闭流
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //获取一个数据库连接
    public static Connection getConnectionAuto() throws SQLException {
        //先获取当前的线程的关联连接对象
        Connection connection=threadLocal.get();
        if(connection==null){
            //返回一个新的连接与当前线程关联
            threadLocal.set(druid.getConnection());
            connection=threadLocal.get();
            //设置为非自动提交
            connection.setAutoCommit(false);
        }
        return connection;
    }
    public static Connection getConnection() throws SQLException {
        return druid.getConnection();
    }
    //关闭资源
    public static void close(Connection connection, Statement statement,ResultSet resultSet){
        if(connection!=null){
            try {
                DbUtils.close(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                DbUtils.close(statement);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
       if (resultSet!=null){
           try {
               DbUtils.close(resultSet);
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }

    }
    //提交并关闭连接
    public static void commitAndClose()  {
        Connection connection=threadLocal.get();
        //不为空说明连接已经被使用过了
        try {
            if (connection!=null){
                connection.commit();//提交事务
                connection.setAutoCommit(true);//重新设置自动提交
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(connection!=null)
                     connection.close();
                //一定要执行remove操作，否则就会出错(因为Tomcat底层使用了线程池技术)，将threadLocal中的关联变量清除
                threadLocal.remove();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    //回滚并关闭连接
    public static void rollbackAndClose(){
        Connection connection=threadLocal.get();
        try {
            if(connection!=null){
                connection.rollback();
                connection.setAutoCommit(true);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                if(connection!=null)
                      connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            threadLocal.remove();
        }
    }
    public static void close( Statement statement,ResultSet resultSet){
        if(statement!=null){
            try {
                DbUtils.close(statement);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (resultSet!=null){
            try {
                DbUtils.close(resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getConnection());
    }

}
