package my.web;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Classname BaseServlet
 * @author: 我心
 * @Description:
 * @Date 2021/10/16 10:34
 * @Created by Lenovo
 */
public class BaseServlet<T>  extends HttpServlet {
    private Class<T> tClass;
    {
//        初始化泛型
        tClass= (Class<T>) this.getClass();
        System.out.println(tClass);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String action=req.getParameter("action");
        //动态分发请求
        //通过反射调用对应的方法
        Class userActionClass =tClass;
        try {
            //获取同名方法
            Method declaredMethod = userActionClass.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println("获取到的方法为"+declaredMethod);
            //调用方法
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//将异常抛给Filter过滤器处理
        }

    }


}
