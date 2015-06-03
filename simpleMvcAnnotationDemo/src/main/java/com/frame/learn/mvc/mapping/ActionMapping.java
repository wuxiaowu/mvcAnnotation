package com.frame.learn.mvc.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-16
 * Time: 下午7:11
 * To change this template use File | Settings | File Templates.
 */
public class ActionMapping {

    public static Map<String, String> actionBeanMap = null;

    public ActionMapping() {

    }

    public static Map<String, String> getActionBeanMap() {
        if (actionBeanMap == null) {
            actionBeanMap = new HashMap<String, String>();
            actionBeanMap.put("com.frame.learn.mvc.form.LoginForm", "com.frame.learn.mvc.action.LoginAction");
            actionBeanMap.put("com.frame.learn.mvc.form.ZhuceForm", "com.frame.learn.mvc.action.ZhuceAction");
        }
        return actionBeanMap;
    }
}
