package com.bbs.interceptor;

import com.bbs.mybatis.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AuthGetcodeInterceptor implements HandlerInterceptor {
    protected Logger logger = LoggerFactory.getLogger(AuthGetcodeInterceptor.class);
    @Value("${appid}")
    private String appid;
    @Value("${secrect}")
    private String secret;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {


    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        this.logger.debug("获取用户请求的url");
        String path = arg0.getContextPath();
        String basePath = arg0.getScheme() + "://" + arg0.getServerName() + path + "/";
        HttpSession session = arg0.getSession();
        String lasturl = null;
        if (arg0.getQueryString() == null) {
            lasturl = arg0.getRequestURL().toString();
        } else {
            lasturl = arg0.getRequestURL() + "?" + arg0.getQueryString();
        }
       /* if (arg0.getSession().getAttribute("existUser") == null) {
            User user = new User();
            user.setAvatar("http://wx.qlogo.cn/mmopen/Sqqm3oJYw2iaVjEQYpczDoC9fTdredJQjzDiazrOUXFoNEEeicGCbYRxDpwLictibRIiaqWl6UFJdYs3m1DCKGbyic2e43O6hxGHLfN/0");
            user.setId(3);
            user.setName("胡礼");
            user.setNickName("hakey");
            user.setOpenid("oc7yYwXP1zU_geQ-DVGEO3pe5D5E");
            arg0.getSession().setAttribute("existUser", user);
        }
        return true;*/
        //先判断用户用有没有登录
        Object existUser = session.getAttribute("existUser");
        if (existUser != null && ((User) existUser).getOpenid() != null) {
            return true;
        } else {
            session.setAttribute("lasturl", lasturl);
            if (arg0.getMethod().toLowerCase().equals("get")) {
                //get 请求
                session.setAttribute("requestMethod", "get");
            } else {
                session.setAttribute("requestMethod", "post");
                //post请求的参数所有参数去除来
                Map<String, String[]> dataMap = new HashMap<String, String[]>();
                Map<String, String[]> parameterMap = arg0.getParameterMap();
                Set<String> keySet = parameterMap.keySet();
                for (String string : keySet) {
                    dataMap.put(string, parameterMap.get(string));
                }
                session.setAttribute("dataMap", dataMap);
            }
            String redirect_url = URLEncoder.encode(basePath + "api/getUserInfo", "utf-8");
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + redirect_url + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
            arg1.sendRedirect(url);
            return false;
        }
    }
}
