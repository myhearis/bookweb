package my.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @Classname ManagerFilter
 * @author: 我心
 * @Description:
 * @Date 2022/1/15 12:53
 * @Created by Lenovo
 */
public class ManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //判断用户是否登录
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        Object user = request.getSession().getAttribute("user");
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        if(user==null){
            //跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/pages/user/login.jsp");
        }
        else
            filterChain.doFilter(request,response);
    }
}
