package com.frame.learn.mvc.listener;

import com.frame.learn.mvc.utils.LoadStrutsConfigUtil;
import com.frame.learn.mvc.utils.StrutsXml;
import com.frame.learn.mvc.utils.XmlBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Win7
 * Date: 15-5-22
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public class ActionListener  implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext=servletContextEvent.getServletContext();
        String xmlPath= servletContext.getInitParameter("struts-config");
        String tomcatPath=servletContext.getRealPath("\\");
        try {
            Map<String ,String> map= LoadStrutsConfigUtil.LoadStrutsConfig(tomcatPath + xmlPath);
            servletContext.setAttribute("struts",map);
        } catch (Exception e) {
            e.printStackTrace();
        }

       System.out.println("信息:系统加载完成!！");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("信息:系统注销完成");
    }
}
