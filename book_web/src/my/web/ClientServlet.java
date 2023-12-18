package my.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import my.pojo.Book;
import my.pojo.Page;
import my.service_impl.BookService;
import my.service_impl.BookServiceImpl;
import my.utils.WebUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Classname ClientServlet
 * @author: 我心
 * @Description:
 * @Date 2021/10/23 11:13
 * @Created by Lenovo
 */
public class ClientServlet extends BaseServlet<ClientServlet>{
   BookService bookService=new BookServiceImpl();
   int min=0,max=0;//用于记录一次价格区间

   //处理价格区间分页的方法
   public void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
       System.out.println("pageByPrice处理价格区间");
       int no,size;
       String min = request.getParameter("min");
       String max = request.getParameter("max");
       //只有提交价格区间表单时才更新价格区间
       if (min!=null&&max!=null){
           this.min = WebUtils.PassInt(min, 0);
           this.max= WebUtils.PassInt(max,0);
       }
       no=WebUtils.PassInt(request.getParameter("pageNo"), 1);
       size=WebUtils.PassInt(request.getParameter("pageSize"),Page.DEFAULT_SIZE);


       //返回一个page对象
       Page<Book> bookPage = bookService.pageRange(this.min, this.max, no, size);
       //获取总的记录数
       int allCount = bookPage.getAllCount();
       //将数据写入请求域中
       request.setAttribute("action","pageByPrice");//说明当前状态是区间分页
       request.setAttribute("pageObj",bookPage);
       request.setAttribute("allCount",allCount);
       request.setAttribute("min",this.min);//回显价格数据
       request.setAttribute("max",this.max);//回显价格数据
       request.setAttribute("pageurl","Client/ClientServlet?action=pageByPrice&min="+this.min+"&max="+this.max);//传给分页使用的pageurl参数
       //传入最新添加的商品名称
       if(request.getParameter("newGoodsName")!=null){
          String name= request.getParameter("newGoodsName");
           System.out.println("获取到的参数："+name);
           request.setAttribute("newGoodsName",name);
       }
       //请求转发
       request.getRequestDispatcher("/pages/Client/index.jsp").forward(request,response);
   }
    public void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int no,size;
        //获取请求参数
        String pageNo = request.getParameter("pageNo");//需要返回第几页
        String pageSize=request.getParameter("pageSize");//获取分页
        //如果页码为负数，则返回第一页，如果页码超过了总页码，则显示最后一页
        no= WebUtils.PassInt(pageNo,1);
        size=WebUtils.PassInt(pageSize, Page.DEFAULT_SIZE);

        //调用Service层方法返回一个对应页的Page对象
        Page page = bookService.page(no, WebUtils.PassInt(pageSize, Page.DEFAULT_SIZE));
        //获取总的记录数
        Long count = bookService.queryForPageTotalCount();
        //将数据写入请求域中
        request.setAttribute("action","page");//说明当前状态是全部分页
        request.setAttribute("pageObj",page);
        request.setAttribute("allCount",count);
        request.setAttribute("pageurl","Client/ClientServlet?action=page");//传给分页使用的pageurl参数
        //传入最新添加的商品名称
        if(request.getParameter("newGoodsName")!=null)
            request.setAttribute("newGoodsName",request.getParameter("newGoodsName"));
        //请求转发
        request.getRequestDispatcher("/pages/Client/index.jsp").forward(request,response);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
