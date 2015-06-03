package com.frame.learn.mvc.action;

import com.frame.learn.mvc.annotation.Controller;
import com.frame.learn.mvc.annotation.RequestMapping;
import com.frame.learn.mvc.form.UserForm;

@Controller
@RequestMapping(value = "/api/frame/user/")
public class UserAction {

   @RequestMapping(value="login")
    public String execute(UserForm userForm) {
        if("admin".equals(userForm.getUsername())&&"123456".equals(userForm.getPassword())){
          return "/view/success.jsp";
        }else{
          return "/view/fail.jsp";
        }
    }
}
