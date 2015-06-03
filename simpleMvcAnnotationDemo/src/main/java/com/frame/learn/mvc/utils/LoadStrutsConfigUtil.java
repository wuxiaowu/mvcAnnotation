package com.frame.learn.mvc.utils;

import com.frame.learn.mvc.annotation.Controller;
import com.frame.learn.mvc.annotation.RequestMapping;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 解析Struts配置文件
 * @author Alan
 *
 */
public class LoadStrutsConfigUtil {

    /**
     * 通过配置文件和注解装载一个Map对象，并返回
     * key值为请求的url，例如/userAction/add.do
     * value值为对应的Action（Control）类+"||"+方法+"||"+方法参数
     *    例如com.struts.business.UserAction||add||com.struts.business.UserForm
     * @param path
     * @return
     * @author Alan
     * @date 2014-6-3 上午11:49:35
     */
    public static Map<String, String> LoadStrutsConfig(String path){
        SAXReader reader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        try {
            Document doc = reader.read(new File(path));
            Element root = doc.getRootElement();

//            map.putAll(scanConfig(root));//扫描按配置文件方式

            map.putAll(scanPackage(root));//扫描注解方式
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return map;
    }

    /**
     * 直接扫描配置文件
     * @param root
     * @return
     * @author Alan
     * @date 2014-5-30 下午6:36:08
     */
    private static Map<String, String> scanConfig(Element root){
        Map<String, String> map = new HashMap<String, String>();
        Element e = root.element("control");
        //类的请求url
        String classurl = e.attributeValue("form-beans");
        //类的路径
        String classpath = e.attributeValue("name");
        Iterator itr= e.elementIterator();
        while(itr.hasNext()){
            Element methodE = (Element) itr.next();
            //方法的请求url
            String methodurl = methodE.attributeValue("url");
            //方法名
            String methodname = methodE.attributeValue("name");
            //方法参数
            String parasname = methodE.attributeValue("paras");
            //装载map   key=类url+方法url+webxml的urlPattern
            //value = 类名||方法名||参数
            map.put(classurl+methodurl, classpath+"||"+methodname+"||"+parasname);
        }
        return map;
    }
    /**
     * 扫描包文件的类
     * @param root
     * @return
     * @author Alan
     * @date 2014-6-3 下午12:07:11
     */
    private static Map<String,String> scanPackage(Element root){
        //获取所有包
        String[] packages = getPackage(root);
        //获取包里所有类
        List<Class> classes = getClasses(packages);
        //解析类的注解
        return parseClass(classes);
    }

    /**
     * 获取配置文件里需要扫描的jar包
     * @param root
     * @return
     * @author Alan
     * @date 2014-5-30 下午6:23:55
     */
    private static String[] getPackage(Element root){
        Element e = root.element("scan");
        if(e==null){
            return null;
        }
        String[] packages = e.attributeValue("package").split(",");
        return packages;
    }

    /**
     * 获取包里所有类文件
     * @param packages
     * @return
     * @author Alan
     * @date 2014-5-30 上午11:47:29
     */
    private static List<Class> getClasses(String[] packages){
        List<Class> list = new ArrayList<Class>();
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        for(String pack : packages){
            File file = new File(classpath+pack.replace(".","/"));
            File[] files = file.listFiles();
            for(File f : files){
                if(!f.isDirectory()){
                    try {
                        String fname = f.getName().substring(0, f.getName().lastIndexOf("."));
                        list.add(Class.forName(pack+"."+fname));
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
    /**
     * 解析类文件的注解
     * @param list
     * @return
     * @author Alan
     * @date 2014-5-30 下午5:04:16
     */
    private static Map<String,String> parseClass(List<Class> list){
        Map<String,String> map= new HashMap<String, String>();
        XmlBean xmlBean=new XmlBean();
        for(Class clazz : list){
            Controller cont = (Controller) clazz.getAnnotation(Controller.class);
            if(cont!=null){
                RequestMapping req_class = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
                Method[] methods = clazz.getMethods();
                for(Method m : methods){
                    //方法的ReqMapping注解
                    RequestMapping req = m.getAnnotation(RequestMapping.class);
                    if(req!=null){
                        StringBuffer paraNames = new StringBuffer();
                        for(Class para : m.getParameterTypes()){
                            //方法的参数
                            paraNames.append(","+para.getName());
                        }
                        String url = req.value();
                        //装载map   key=类url+方法url+webxml的urlPattern
                        //value = 类名||方法名||参数
                        map.put(req_class.value()+url, clazz.getName()+"||"+m.getName()+"||"+paraNames.substring(1));
                    }
                }
            }

        }
        return map;
    }
    public static void main(String[] args){
		LoadStrutsConfig(Thread.currentThread().getContextClassLoader().getResource("").getPath());
    }
}