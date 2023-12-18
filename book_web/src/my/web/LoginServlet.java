package my.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import my.pojo.User;
import my.service_impl.UserServiceImpl;

import java.io.IOException;

/**
 * @Classname LoginServlet
 * @author: 我心
 * @Description: 负责登录界面的小程序
 * @Date 2021/10/3 12:35
 * @Created by Lenovo
 */
public class LoginServlet extends HttpServlet {
    UserServiceImpl userService;//服务层对象
    {
        userService=new UserServiceImpl();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求域中的数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //将域数据封装为一个对象
        User newUser = new User(username, null, password);
        if(userService.Login(newUser)){
            //返回真，登陆成功,跳转登录成功页面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
        else {
            String message;
            //用户名存在，密码错误的情况
            if( userService.isExistName(username))
                message="输入的密码有误！";
            else
                message="用户名不存在！";
            //将错误和信息存储到request的域当中
            req.setAttribute("username",username);//存储用户名
            req.setAttribute("message",message);
            System.out.println("登录失败");
            //登陆失败
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }
    }
}
