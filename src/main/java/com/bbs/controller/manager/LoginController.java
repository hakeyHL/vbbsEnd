package com.bbs.controller.manager;

import com.bbs.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lihongde on 2016/9/8 15:42
 */
@Controller
public class LoginController extends BaseController{

    /**
     * @return
     */
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String username, String password) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            mav.setViewName("index");
            mav.addObject("error", "用户名/密码不能为空");
            return mav;
        }
        if(!username.equals("admin")){
            mav.setViewName("index");
            mav.addObject("error", "用户名输入错误");
            return mav;
        }
        if(!password.equals("admin")){
            mav.setViewName("index");
            mav.addObject("error", "密码输入错误");
            return mav;
        }
        try {
            mav.setViewName("system/home");
            return mav;
        } catch (RuntimeException e) {
            mav.setViewName("index");
            mav.addObject("error", "未知错误，请联系管理员");
            return mav;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "index";
    }
}
