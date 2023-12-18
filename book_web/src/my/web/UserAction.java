package my.web;
import com.google.code.kaptcha.Constants;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.pojo.ShoppingCart;
import my.pojo.User;
import my.service_impl.UserService;
import my.service_impl.UserServiceImpl;
import my.utils.CookieUtils;
import my.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * @Classname UserService
 * @author: 我心
 * @Description:
 * @Date 2021/10/15 22:56
 * @Created by Lenovo
 */
public class UserAction extends BaseServlet <UserAction>{
    UserService userService=new UserServiceImpl();
    //Ajax发起请求判断用户名是否存在的方法
    public void ajaxUsernameIsExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("收到了用户名请求");
        String[] usernames = request.getParameterValues("username");
        String name=usernames[0];
        System.out.println("数据为："+name);
        String msg="";
        Gson gson = new Gson();//java与json交互的工具类
        //判断是否存在
        if(userService.isExistName(name)){
           //存在，返回true
            String s = gson.toJson(true);
            response.getWriter().write(s);
        }
        else
             response.getWriter().write(gson.toJson(false));
    }
    //注销用户的方法
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("user",null);
        //直接请求重定向回首页
        response.sendRedirect(request.getContextPath()+"/pages/user/login.jsp");
    }
    //登录方法
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServletException, jakarta.servlet.ServletException {
        //获取请求域中的数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //将域数据封装为一个对象
        User newUser = new User(username, null, password);
        if(userService.Login(newUser)){
            //返回真，登陆成功,跳转登录成功页面
            //新建一个Cookie对象
            Cookie cookie=new Cookie("BookUserName",newUser.getUsername());
            //设置路径限制
            cookie.setMaxAge(3600*24*3);//保存三天
            //创建一个session对象保存当前进入会话中的用户
            HttpSession session = req.getSession();
            //设置为永久存储
            session.setMaxInactiveInterval(-1);
//            req.getSession()
            session.setAttribute("user",newUser);//将用户信息临时保存到session域中
            //获取用户的购物车信息，如果没有，则创建一个购物车对象(规定名称为：用户名:shoppingCart)
            Object userShopping = session.getAttribute(newUser.getUsername() + ":shoppingCart");
            if (userShopping==null)
                session.setAttribute(newUser.getUsername()+":shoppingCart",new ShoppingCart());

            //通知浏览器接收
            resp.addCookie(cookie);
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
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ServletException, jakarta.servlet.ServletException {
        //1.获取请求域中的数据
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userCode=req.getParameter("code");//用户传入的验证码
        String code= (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        String code = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);//获取谷歌验证码
        System.out.println("谷歌验证码："+code);
        //删除谷歌验证码
        req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,null);
        //2、检查验证码是否正确，暂时写死为abcde,忽略大小写
        if(userCode.equalsIgnoreCase(code)){
            //正确
            //检查用户名是否可用
            if(!userService.isExistName(username)){
                //可用,将对象写入数据库中
                //使用beanUtils注入参数,数据封装到对象中
                User newUser = WebUtils.ObjectSet(req.getParameterMap(),User.class);
                //数据封装到对象中
                //写入数据库
                boolean b = userService.registerUser(newUser);
                if(b){
                    System.out.println("注册成功！");
                    //跳转到注册成功页面
                    //给浏览器保存一个用户名的Cookie
                    Cookie cookie = new Cookie("BookUserName", newUser.getUsername());
                    resp.addCookie(cookie);//通知浏览器接收
                    //更新session用户对象
                    req.getSession().setAttribute("user",newUser);
                    //获取用户的购物车信息，如果没有，则创建一个购物车对象(规定名称为：用户名:shoppingCart)
                    HttpSession session= req.getSession();
                    Object userShopping = session.getAttribute(newUser.getUsername() + ":shoppingCart");
                    if (userShopping==null)
                        session.setAttribute(newUser.getUsername()+":shoppingCart",new ShoppingCart());
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws jakarta.servlet.ServletException, IOException {
        doPost(req,resp);
    }
}
