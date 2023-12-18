package my.dao_impl;

import my.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname BaseDao
 * @author: 我心
 * @Description: 基础的dao类
 * @Date 2021/10/1 18:07
 * @Created by Lenovo
 */
public abstract class BaseDao {
    private QueryRunner queryRunner=new QueryRunner();//工具类的对象
    //更新操作,返回受影响的行数，返回-1表示更新失败
    public int update(Connection connection,String sql,Object...args) throws SQLException {
        int i=-1;
        i = queryRunner.update(connection, sql);
        //关闭连接
      //  connection.close();
        return i;

    }
    //更新操作,返回受影响的行数，返回-1表示更新失败
    public int updateTransaction(Connection connection,String sql,Object...args) throws SQLException {
        return queryRunner.update(connection, sql,args);

    }
    //查询并返回一个对象
    public <T>T queryForOne(Class<T> tClass,Connection connection,String sql,Object...args) throws SQLException {
        //创建对应的结果集接口的实现类
        BeanHandler<T> tBeanHandler = new BeanHandler<>(tClass);
        T query = queryRunner.query(connection, sql, tBeanHandler, args);
//        connection.close();
        return query;
    }
    //事务处理时，不会关闭连接
    public <T>T queryForOneTransaction(Class<T> tClass,Connection connection,String sql,Object...args) throws SQLException {
        //创建对应的结果集接口的实现类
        BeanHandler<T> tBeanHandler = new BeanHandler<>(tClass);
        T query = queryRunner.query(connection, sql, tBeanHandler, args);
        return query;
    }
    //返回查询多个对象的集合
    public <T> List<T> queryForList(Class<T> tClass,Connection connection,String sql,Object...args) throws SQLException {
        BeanListHandler<T> listHandler=new BeanListHandler<T>(tClass);
        List<T> query = queryRunner.query(connection, sql, listHandler, args);
        return query;
    }
    public <T> List<T> queryForListTransaction(Class<T> tClass,Connection connection,String sql,Object...args) throws SQLException {
        BeanListHandler<T> listHandler=new BeanListHandler<T>(tClass);
        List<T> query = queryRunner.query(connection, sql, listHandler, args);
        return query;
    }
    //返回单个值
    public Object getValue(Connection connection,String sql,Object...args) throws SQLException {
        ScalarHandler scalarHandler=new ScalarHandler();//返回单个值的接口的实现类,接口方法可以将结果集注入到对应的对象中
        return queryRunner.query(connection,sql,scalarHandler,args);
    }
}
