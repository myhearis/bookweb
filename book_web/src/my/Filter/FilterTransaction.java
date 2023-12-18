package my.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.utils.JDBCUtils;

import java.io.IOException;

/**
 * @Classname FilterTransaction
 * @author: 我心
 * @Description:
 * @Date 2022/1/17 13:54
 * @Created by Lenovo
 */
public class FilterTransaction implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  {
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;

        try {
            filterChain.doFilter(servletRequest,servletResponse);
            //提交事务
            JDBCUtils.commitAndClose();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            JDBCUtils.rollbackAndClose();
            //再将异常抛出给服务器，服务器捕获异常后，展示友好的错误页面
            throw new RuntimeException(e);
        }
    }
}
