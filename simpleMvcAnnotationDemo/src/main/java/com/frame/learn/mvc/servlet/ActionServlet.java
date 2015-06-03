package com.frame.learn.mvc.servlet;

import com.frame.learn.mvc.mapping.ActionMapping;
import com.frame.learn.mvc.utils.FullBean;
import com.frame.learn.mvc.utils.XmlBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

public class ActionServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
      String ServletPath=  request.getServletPath();
      String path= this.getPath(ServletPath);

        Map<String,String> map =(Map<String,String>) this.getServletContext().getAttribute("struts");
        String executeInfo=map.get(path);
       String  [] executeInfos=executeInfo.split("\\|\\|");

        Object url="";
        String[] parasName = executeInfos[2].split(",");//第二个参数是方法参数
        Class[] paras = new Class[parasName.length];
        for(int i = 0; i < parasName.length;i++){
            try {
                paras[i] = Class.forName(parasName[i]);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            Class clazz=Class.forName(executeInfos[0]);
            Object controller=clazz.newInstance();
            Method  method=clazz.getMethod(executeInfos[1],paras);

            Object[] parasInstaces = new Object[paras.length];
            for (int i = 0; i < paras.length; i++) {
                //装配方法参数
                Object paraInstance = FullBean.fullBean(request,parasName[i]);
                parasInstaces[i] = paraInstance;
            }
            url= method.invoke(controller,parasInstaces);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher=request.getRequestDispatcher(url.toString());
        dispatcher.forward(request,response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    public String getPath(String path){
        return path.split("\\.")[0];
    }
}
