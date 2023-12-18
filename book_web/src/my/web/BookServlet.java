package my.web;



import my.pojo.Book;
import my.pojo.Page;
import my.service_impl.BookService;
import my.service_impl.BookServiceImpl;
import my.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Classname BookServlet
 * @author: 我心
 * @Description:
 * @Date 2021/10/16 22:18
 * @Created by Lenovo
 */
public class BookServlet extends BaseServlet<BookServlet>{
    private BookService bookService=new BookServiceImpl();
    //分页方法
    public void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int no,size;
        //获取请求参数
        String pageNo = request.getParameter("pageNo");//需要返回第几页
        String pageSize=request.getParameter("pageSize");//获取分页
        //如果页码为负数，则返回第一页，如果页码超过了总页码，则显示最后一页
        no=WebUtils.PassInt(pageNo,1);
        size=WebUtils.PassInt(pageSize,Page.DEFAULT_SIZE);

        //调用Service层方法返回一个对应页的Page对象
        Page page = bookService.page(no, WebUtils.PassInt(pageSize, Page.DEFAULT_SIZE));

        //获取总的记录数
        Long count = bookService.queryForPageTotalCount();
        System.out.println(count);
        //将数据写入请求域中
        request.setAttribute("pageObj",page);
        request.setAttribute("allCount",count);
        request.setAttribute("pageurl","manager/BookServlet?action=page");//pageurl作为分页的参数
        //请求转发
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
    }
//    添加图书
    public void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("调用了addBook方法");
        //获取添加的页
        String pageNo = request.getParameter("pageNo");
        //使其永远都在最后一页，也就是让当前添加的页码+1，防止添加之后是总页数改变的情况
        int No=Integer.parseInt(pageNo)+1;
        //将参数封装到对象中
        Book book = WebUtils.ObjectSet(request.getParameterMap(), Book.class);
        //将对象写入数据库中
        boolean b = bookService.addBook(book);
        if (b){
            System.out.println("添加图书成功！");
//            请求重定向(跳转到最后一页)
            response.sendRedirect(request.getContextPath()+"/manager/BookServlet?action=page&pageNo="+No);

        }
        else {
            throw new Exception("添加图书失败！");
        }
    }
    //更新图书的方法
    public void update(HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        //获取当前修改的页码
        String pageNoUpdate = request.getParameter("pageNoUpdate");
        //将域数据封装到对象中
        Book book = WebUtils.ObjectSet(request.getParameterMap(), Book.class);
        //更新到数据库中
        boolean b = bookService.updateBook(book);
        if (b){
            //请求重定向
            //请求重定向,防止重复修改，并更新图书信息
            response.sendRedirect(request.getContextPath()+"/manager/BookServlet?action=page&pageNo="+pageNoUpdate);
        }
    }
    //给编辑页面返回图书的方法
    public void getupdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求的id
        String id = request.getParameter("id");
        //获取当前修改页
        String pageNo = request.getParameter("pageNo");
        //写入域数据
        request.setAttribute("pageNo",pageNo);
        //拉取数据
        Book book = bookService.queryBookById(Integer.parseInt(id));
        //传入数据给请求域数据
        request.setAttribute("updatebook",book);
        //请求转发到编辑页面
        request.getRequestDispatcher("/pages/manager/book_edit.jsp?action=update").forward(request,response);
    }
//    删除图书的方法
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
       //获取删除图书所在的页数
        String pageNo = request.getParameter("pageNo");
        //获取删除图书的id
        String id = request.getParameter("id");
        //调用service对象删除
        boolean b = bookService.deleteBookById(Integer.parseInt(id));
        if (b){
            //请求重定向,防止重复删除，并更新图书信息
            response.sendRedirect(request.getContextPath()+"/manager/BookServlet?action=page&pageNo="+pageNo);
        }
    }
    //将所有的图书列表写入request的域中
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("调用了list方法");
        //1.获取数据
        List<Book> books = bookService.queryBooks();
        //2.写入域数据
        request.setAttribute("list",books);
        System.out.println("list中写入域的booklist为"+books);
        //3.请求转发到对应的jsp页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req,resp);
    }
}
