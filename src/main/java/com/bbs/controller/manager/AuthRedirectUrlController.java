package com.bbs.controller.manager;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.bbs.controller.BaseController;
import com.bbs.mybatis.model.User;
import com.bbs.service.IUserService;
import com.bbs.util.HttpClientUtil;
@Controller
public class AuthRedirectUrlController extends BaseController {

	@Value("${appid}")
	private String appid;
	@Value("${secrect}")
	private String secret;
	@Autowired
	private IUserService iUserService;
	@RequestMapping(value="/api/getUserInfo",method=RequestMethod.GET)
	public String getUserInfo(String code,String state,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		if(StringUtils.isNotBlank(code)){
			this.logger.debug("=========================================获取code成功");
			String ladtRequestMethod=(String) request.getSession().getAttribute("requestMethod");
			String lasturl=(String) request.getSession().getAttribute("lasturl");
			String access_token;
			String result = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code");
			JSONObject obj=JSONObject.parseObject(result);
			if(obj.containsKey("access_token")){
				this.logger.debug("=========================================获取access_token成功");
				
				//获取用户的openid去库里检索
				User queryUser=this.iUserService.findUserByOpenid(obj.getString("openid"));
				if(queryUser!=null&&StringUtils.isNotBlank(queryUser.getOpenid()))
				{
					//帮用户完成自动登录
					this.logger.debug("=========================================帮用户完成了自动登录");
					request.getSession().setAttribute("existUser", queryUser);
					if("get".equals(ladtRequestMethod)){
						this.logger.debug("=========================================自动登录get 请求跳转完成+===="+lasturl);
						return "redirect:"+lasturl;
					}else{
						this.logger.debug("=========================================自动登录post 请求跳转完成====auth/logic");
						return "auth/logic";
					}
				}
				this.logger.debug("=========================================没有完成自动登录 需要重新获取用户信息");
				//在数据库中检索不到 说明是新的用户 拉去用户信息并入库
				access_token=obj.getString("access_token");
				//检查有没有过期
				String isDeprecatedRe = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/auth?access_token="+access_token+"&openid="+obj.getString("openid"));
				JSONObject isDeprecatedReObj=JSONObject.parseObject(isDeprecatedRe);
				if(isDeprecatedReObj.getString("errmsg")!="ok"){
					this.logger.debug("=========================================access_token失效");
					//access_token无效 刷新access_token
					String temp = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appid+"&grant_type=refresh_token&refresh_token="+obj.getString("refresh_token"));
					JSONObject refreshAccessObj=JSONObject.parseObject(temp);
					if(refreshAccessObj.containsKey("access_token")){
						this.logger.debug("=========================================刷新access_token成功");
						access_token=refreshAccessObj.getString("access_token");
					}else{
						this.logger.debug("=========================================刷新access_token失败");
						return "auth/fail";
					}
					
				}
				//获取用户的个人信息
				String usrInfoStr = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+obj.getString("openid")+"&lang=zh_CN");
				JSONObject usrInfoObj=JSONObject.parseObject(usrInfoStr);
				if(usrInfoObj.containsKey("nickname")){
					this.logger.debug("=========================================拉取用户跟人信息成功");
					//存储用户的个人信息
					User user=new User();
					user.setAvatar(usrInfoObj.getString("headimgurl"));
					user.setNickName(usrInfoObj.getString("nickname"));
					user.setOpenid(usrInfoObj.getString("openid"));
					this.iUserService.addUser(user);
					//往session存用户的数据
					request.getSession().setAttribute("existUser", user);
					if("get".equals(ladtRequestMethod)){
						return "redirect:"+lasturl;
					}else{
						return "auth/logic";
					}
					
				}else{
					this.logger.debug("=========================================拉取用户跟人信息失败");
					return "auth/fail";
				}	
			}else{
				this.logger.info("=========================================获取access_token失败");
				return "auth/fail";
			}
		}else{
			return "auth/fail";
		}
	}
}
