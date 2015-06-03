package com.frame.learn.mvc.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-21
 * Time: 下午10:14
 * To change this template use File | Settings | File Templates.
 */
public class XmlBean {


    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


    public String getFormClass() {
        return formClass;
    }

    public void setFormClass(String formClass) {
        this.formClass = formClass;
    }

    public Map<String, String> getActionForward() {
        return actionForward;
    }

    public void setActionForward(Map<String, String> actionForward) {
        this.actionForward = actionForward;
    }

    private String  beanName="";

    private String path="";

    private String actionType="";


    private String formClass="";


    private Map<String ,String > actionForward=new HashMap<String,String>();





}
