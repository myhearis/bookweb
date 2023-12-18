package my.service_impl;

import my.dao_impl.UserImpl;
import my.pojo.User;
import my.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Classname UserServiceImpl
 * @author: 我心
 * @Description: 业务层实现类
 * @Date 2021/10/2 13:48
 * @Created by Lenovo
 */
public class UserServiceImpl implements UserService{
    //对应表的dao对象
    private UserImpl userImpl=new UserImpl();
    //获取连接
    public Connection getConnection() throws SQLException {
        return JDBCUtils.getConnection();
    }
    //关闭连接
    public void releaseResources(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException throwables) {

                throwables.printStackTrace();
            }
        }
    }
    @Override
    public boolean registerUser(User user) {
        //判断用户名是否存在
        if(isExistName(user.getUsername())){
            return false;
        }
        else {
            Connection connection=null;
            try {
                connection = JDBCUtils.getConnection();
                int user1 = userImpl.createUser(connection, user);
                connection.close();
//            影响行数大于0说明保存成功
                if(user1>0)
                    return true;
                else return false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {
              releaseResources(connection);
            }
        }
       return false;
    }

    @Override
    public boolean Login(User user) {
        //在数据库中能查到对应的对象则登陆成功
        Connection connection = null;
        User userByNamePassword=null;
        try {
            connection = getConnection();
            userByNamePassword = userImpl.getUserByNamePassword(connection, user.getUsername(), user.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //关闭连接
        releaseResources(connection);
//        登陆成功
        if(userByNamePassword!=null)
            //不为空返回true，登陆成功
            return true;
//        失败
        else
            return false;
    }

    @Override
    public boolean isExistName(String username) {
        Connection connection = null;
        User userByName=null;
        try {
            connection = getConnection();
            userByName = userImpl.getUserByName(connection, username);
            //存在返回true
            if (userByName!=null){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }
}

