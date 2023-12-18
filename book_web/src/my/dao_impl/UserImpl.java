package my.dao_impl;

import my.pojo.User;
import my.pojo.UserDao;
import my.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Classname UserImpl
 * @author: 我心
 * @Description:
 * @Date 2021/10/1 18:44
 * @Created by Lenovo
 */
public class UserImpl extends BaseDao implements UserDao {
    //这里的方法暂时没有自动关闭连接，因为要考虑事务处理
    @Override
    public User getUserByName(Connection connection, String username) {
        User user=null;
        String sql="SELECT * FROM t_user WHERE username=?;";
        try {
            user = queryForOneTransaction(User.class, connection, sql, username);
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
//通过账号密码查询
    @Override
    public User getUserByNamePassword(Connection connection, String username, String password) {
        User user=null;
        String sql="SELECT * FROM t_user WHERE username=? and password=?;";
        try {
            user =queryForOneTransaction(User.class, connection, sql, username,password);
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;

    }

    @Override
    public int createUser(Connection connection, User user) {
        int i=-1;
        String sql="INSERT INTO t_user(username,password,email) VALUES(?,?,?)";
        try {
            return updateTransaction(connection, sql, user.getUsername(), user.getPassword(), user.getEmail());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return i;
    }
}

class T{
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        UserImpl user = new UserImpl();
        User mydackkk = user.getUserByName(connection, "mydackkk");
        int user1 = user.createUser(connection, mydackkk);
    }
}
