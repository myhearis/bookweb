package my.pojo;

import java.sql.Connection;

/**
 * @Classname UserDao
 * @author: 我心
 * @Description: User表接口规范
 * @Date 2021/10/1 18:40
 * @Created by Lenovo
 */
public interface UserDao {
    //根据用户名查找用户
    User getUserByName(Connection connection, String username);
    //根据用户名和密码查找用户
    User getUserByNamePassword(Connection connection,String username,String password);
    //创建一个新用户，写入数据库,返回影响的行数
    int createUser(Connection connection,User user);

}
