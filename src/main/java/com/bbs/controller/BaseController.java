package com.bbs.controller;


import com.bbs.service.*;
import com.bbs.util.Constants;
import com.bbs.util.DateEditor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(BaseController.class);
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected String basePath;
	protected Integer page;
	protected Integer size;
	@Autowired
	protected ApiService apiService;
	@Autowired
	protected IPostService postService;
	@Autowired
	protected IPostCommentService postCommentService;
	@Autowired
	protected IUserService userService;
	@Autowired
	protected IVoteService voteService;



	public BaseController() {
		super();
	}

	public static void main(String[] args) throws Exception {
		String testString = "微信评论测试";
		byte[] bytes = testString.getBytes("utf-8");
		String tempString = new String(bytes, "iso-8859-1");
		System.out.println(tempString);
	}

	/**
	 * 定义日期类型的数据绑定
	 *
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	@ModelAttribute
	protected void initRequestResponseSession(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();

		String path = request.getContextPath();
		basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

		String page_str = request.getParameter("page");
		String size_str = request.getParameter("size");

		if (StringUtils.isNotEmpty(page_str)) {
			page = Integer.parseInt(page_str);
		}else{
            page = Constants.DEFAULT_PAGE;
        }
		if (StringUtils.isNotEmpty(size_str)) {
            size = Integer.parseInt(size_str);
		}else{
            size = Constants.DEFAULT_PAGE_SIZE;
        }
	}

}
