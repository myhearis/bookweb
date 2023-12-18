package my.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


import my.pojo.User;
import my.service_impl.UserServiceImpl;

import java.io.IOException;

/**
 * @Classname RegisterServlet
 * @author: 我心
 * @Description:
 * @Date 2021/10/2 22:04
 * @Created by Lenovo
 */
public class RegisterServlet extends HttpServlet {
    //业务层对象
    private UserServiceImpl userService=new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求域中的数据
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String code = req.getParameter("code");
      //2、检查验证码是否正确，暂时写死为abcde,忽略大小写
        if(code.equalsIgnoreCase("abcde")){
            //正确
            //检查用户名是否可用
            if(!userService.isExistName(username)){
                //可用,将对象写入数据库中
                User newUser = new User(username, email, password);//封装到对象中
                //写入数据库
                boolean b = userService.registerUser(newUser);
                if(b){
                    System.out.println("注册成功！");
                    //跳转到注册成功页面
                    req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
                }
                else {
                    req.setAttribute("message","数据库写入数据失败，请检查！");
                    req.setAttribute("email",email);//回显邮箱
                    req.setAttribute("username",username);//回显用户名
                    System.out.println("注册失败，请检查");
                    req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
                }

            }
            //不可用
            else {
                req.setAttribute("message","用户名不可用！");
                req.setAttribute("email",email);//回显邮箱
                req.setAttribute("username",username);//回显用户名
                //错误,返回原注册页面
                System.out.println("用户名不可用");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }
        }
        else {
            req.setAttribute("message","验证码错误！");
            req.setAttribute("email",email);//回显邮箱
            req.setAttribute("username",username);//回显用户名
            System.out.println("验证码错误");
            //错误,返回原注册页面
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }

    }
}
