package my.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Classname webUtils
 * @author: 我心
 * @Description:封装BeanUtils方法的工具类，用于给对象注入参数
 * @Date 2021/10/16 11:14
 * @Created by Lenovo
 */
public class WebUtils {
    public static void ObjectSet(Object obj, HttpServletRequest req) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(obj,req.getParameterMap());
    }
    public static <T>T ObjectSet(Map map, Class<T> tClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Constructor<T> constructor = tClass.getConstructor();
        //反射获取对象
        T t = constructor.newInstance();
        BeanUtils.copyProperties(t,map);//注入参数
        return t;
    }
    //如果传入的转换字符串为空，则返回默认值
    public static int PassInt(String numStr,int defaultInt){
        if(numStr==null||numStr.length()==0)
            return defaultInt;
        return Integer.parseInt(numStr);

    }
}
