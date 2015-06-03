package com.frame.learn.mvc.utils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-21
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
public class StrutsXml {

    public static Map<String, XmlBean> getActionMapping(String xmlPath) throws Exception {

        // 解析xml
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new File(xmlPath));
        Element root = document.getRootElement();
        Element actionMapping=root.getChild("action-mapping");
        List<Element> actionList=actionMapping.getChildren();
        String path="";
        Map<String, XmlBean> xmlBeanMap=new HashMap<String,XmlBean>();
        for (Element action: actionList){
            XmlBean xmlBean=new XmlBean();
            String name=action.getAttributeValue("name");
            path=action.getAttributeValue("path");
            String type= action.getAttributeValue("type");
            xmlBean.setActionType(type);
            xmlBean.setPath(path);
            Element actionFom=root.getChild("form-beans");
            List<Element> formList=actionFom.getChildren();
            for (Element form: formList){
                if(name.equals(form.getAttributeValue("name"))){
                    String formClass=form.getAttributeValue("class");
                    xmlBean.setBeanName(formClass);
                    xmlBean.setFormClass(formClass);
                    break;
                }
            }
            List<Element> forwardList=action.getChildren();
            Map<String,String> forwardMap=new HashMap<String, String>();
            for (Element  forward: forwardList){
                String forwardName=forward.getAttributeValue("name");
                String forwardValue=forward.getAttributeValue("value");
                forwardMap.put(forwardName,forwardValue);
            }
            xmlBean.setActionForward(forwardMap);
            xmlBeanMap.put(path,xmlBean);
        }
        return xmlBeanMap;
    }

    public static  void main(String [] args) throws  Exception{
       new StrutsXml().getActionMapping("\\WEB-INF\\struts-config.xml");
    }
}
