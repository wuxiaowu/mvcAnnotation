package com.frame.learn.mvc.utils;




import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class FullBean {

    public static Object fullBean(HttpServletRequest request, String className){
        Object o=null;
        try {
          Class clazz=  Class.forName(className);
          o= clazz.newInstance();
          Field[] fields=clazz.getDeclaredFields();
          for(Field f: fields){
              f.setAccessible(true);
              f.set(o,request.getParameter(f.getName()));
              f.setAccessible(false);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
