package my.utils;


import jakarta.servlet.http.Cookie;

/**
 * @Classname CookieUtils
 * @author: 我心
 * @Description:Cookie的工具类
 * @Date 2021/10/26 22:15
 * @Created by Lenovo
 */
public class CookieUtils {
    
    public static Cookie getCookie(String name, Cookie[] cookies){
        Cookie cookie=null;
        for (Cookie cookie2 : cookies) {
            if (cookie2.getName().equals(name)){
                cookie=cookie2;
                break;
            }
        }
        return cookie;
    }
}
