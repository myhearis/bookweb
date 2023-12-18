package my.utils;

import jakarta.servlet.http.HttpSession;
import my.pojo.ShoppingCart;
import my.pojo.User;


/**
 * @Classname ShoppingUtils
 * @author: 我心
 * @Description:购物模块工具类
 * @Date 2021/10/30 18:41
 * @Created by Lenovo
 */
public class ShoppingUtils {
    public static ShoppingCart getUserShoppingCart(HttpSession session, User user){
        ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute(user.getUsername() + ":shoppingCart");
        if (shoppingCart==null)
            return null;
        return shoppingCart;
    }
    public static ShoppingCart getUserShoppingCart(HttpSession session){
        //先找到对应的user对象
        User user = (User) session.getAttribute("user");
        if (user==null)
            return null;
        return getUserShoppingCart(session,user);
    }
    //通过session获取当前用户
    public static User getThisUser(HttpSession session){
        User user=null;
        if (session.getAttribute("user")!=null)
            user= (User) session.getAttribute("user");
        return user;
    }
}
