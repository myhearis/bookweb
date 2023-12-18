package my.service_impl;

import my.pojo.User;

import java.sql.SQLException;

/**
 * @Classname UserService
 * @author: 我心
 * @Description:业务层
 * @Date 2021/10/2 13:43
 * @Created by Lenovo
 */
public interface UserService {
    //注册用户
    boolean registerUser(User user) throws SQLException;
    //用户登录
    boolean Login(User user);
    //判断用户名是否存在
    boolean isExistName(String username);

}
