package my.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.pojo.Goods;
import my.pojo.ShoppingCart;
import my.pojo.User;
import my.utils.ShoppingUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;

/**
 * @Classname ShoppingServlet
 * @author: 我心
 * @Description:
 * @Date 2021/10/30 13:14
 * @Created by Lenovo
 */
public class ShoppingServlet extends BaseServlet<ShoppingServlet>{
    //删除购物车商品
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取对应商品的id
        String id = req.getParameter("id");
        //删除购物车中对应的id的商品
        ShoppingCart userShoppingCart = ShoppingUtils.getUserShoppingCart(req.getSession());
        userShoppingCart.deleteGoods(Integer.parseInt(id));
        //删除之后，重新进入购物车界面
        //重定向回购物车
        resp.sendRedirect(req.getContextPath()+"/pages/cart/cart.jsp");
    }
    //使用ajax请求添加商品到购物车
    public void ajaxAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求信息
        System.out.println("执行添加方法");
        String id = req.getParameter("id");
        String price = req.getParameter("price");
        String name = req.getParameter("name");
        //封装成商品项
        Goods goods = new Goods(Integer.parseInt(id), name, Double.parseDouble(price));
        //获取购物车对象
        ShoppingCart userShoppingCart = ShoppingUtils.getUserShoppingCart(req.getSession());
        userShoppingCart.addGoods(goods);//添加商品
        //回传json字符串
        Gson gson = new Gson();
        Hashtable<String, Object> data = new Hashtable<>();
        data.put("shoppingCartCount",userShoppingCart.getTotalCount());
        data.put("goodsName",goods.getName());
        //转成json字符串
        String strData = gson.toJson(data, new TypeToken<Hashtable<String, Object>>() {
        }.getType());
        resp.getWriter().write(strData);//回传

    }
    //更新商品数量的方法
    public void updateCount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取id和对应的数量
        String id = req.getParameter("id");
        String count = req.getParameter("count");
        //获取当前用户的购物车对象
        ShoppingCart userShoppingCart = ShoppingUtils.getUserShoppingCart(req.getSession());
        userShoppingCart.modifyGoods(Integer.parseInt(id),Integer.parseInt(count));
        //重定向回购物车
        resp.sendRedirect(req.getContextPath()+"/pages/cart/cart.jsp");
    }
    //清空购物车的方法
    public void clear(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取当前账户的购物车对象
        ShoppingCart userShoppingCart = ShoppingUtils.getUserShoppingCart(req.getSession());
        userShoppingCart.clear();
        //重定向回购物车界面
        resp.sendRedirect(req.getContextPath()+"/pages/cart/cart.jsp");
    }
    //添加商品到购物车
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pageurl = req.getParameter("pageurl");
        String pageNo = req.getParameter("pageNo");
        String go=req.getContextPath()+"/"+pageurl+"&pageNo="+pageNo;
        System.out.println(go);
        //获取请求域中的数据
        System.out.println("执行添加方法");
        String id = req.getParameter("id");
        String price = req.getParameter("price");
        String name = req.getParameter("name");
        //封装成一个商品项
        Goods goods = new Goods(Integer.parseInt(id), name, Double.parseDouble(price));
//        System.out.println(goods);
        //调用购物车对象的添加方法
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute(user.getUsername() + ":shoppingCart");
        shoppingCart.addGoods(goods);//添加到购物车中
        byte[] bytes = goods.getName().getBytes("UTF-8");
        String s = new String(bytes,"UTF-8");
        System.out.println("转换后的名字:"+s);
        //请求重定向，防止重复添加
        resp.sendRedirect(go+"&newGoodsName="+s);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
