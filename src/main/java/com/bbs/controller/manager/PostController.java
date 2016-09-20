package com.bbs.controller.manager;

import com.alibaba.fastjson.JSON;
import com.bbs.controller.BaseController;
import com.bbs.mybatis.model.Post;
import com.bbs.mybatis.model.PostImage;
import com.bbs.service.IPostService;
import com.bbs.service.ISectionService;
import com.bbs.service.IUserService;
import com.bbs.util.ApiJsonResult;
import com.bbs.util.Constants;
import com.bbs.util.FileUtils;
import com.bbs.util.TextUtils;
import com.bbs.util.page.Page;
import com.bbs.util.page.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lihongde on 2016/9/8 16:29
 */
@Controller
@RequestMapping(value = "/post")
public class PostController extends BaseController {

    @Resource
    private IPostService postService;

    @Resource
    private ISectionService sectionService;

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPosts() {
        return "redirect:/post/find";
    }


    /**
     * 条件查询帖子列表
     * @param section
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView findPosts(String section) {
        ModelAndView mav = new ModelAndView("/post/list");
        Page<Post> pages = postService.findPagePosts(section, page, size);
        mav.addObject("page", PageHelper.getPageModel(request, pages));
        List<Post> postList = new ArrayList<Post>();
        for (Post post : pages.getItems()) {
            post.setUser(userService.findUserById(post.getUserId()).getNickName());
            post.setSection(sectionService.getSection(post.getSectionId()).getName());
            postList.add(post);
        }
        mav.addObject("posts", postList);
        return mav;
    }

    @RequestMapping(value = "/publish")
    public ModelAndView publish() {
        ModelAndView mav = new ModelAndView("post/add");
        mav.addObject("sections", JSON.toJSON(sectionService.getAllSection()));
        return mav;
    }

    /**
     * 发帖或修改帖子
     * @param post
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrUpdate(Post post, String files) {
        post.setPublishTime(new Date());
        post.setStatus(Constants.POST_STATUS.COMMON);
        post.setIsApprove(Constants.YES_OR_NO.YES);
        post.setUserId(Constants.ADMIN_USER_ID);
        post.setIsDelete(Constants.YES_OR_NO.NO);
        if(post.getTop() == null){
            post.setTop(Constants.YES_OR_NO.NO);
        }else{
            post.setTop(Constants.YES_OR_NO.YES);
        }

        String content = post.getContent();
        List<String> imgList = TextUtils.getImgSrc(content);
        post.setContent(TextUtils.delHTMLTag(content));

        postService.addOrUpdate(post);

        for(String imgUrl : imgList){
            PostImage pm = new PostImage();
            pm.setPostId(post.getId());
            pm.setImgUrl(imgUrl);
            postService.addPostImage(pm);
        }


        return "redirect:/post/find";
    }


    /**
     * 帖子详情
     * @param postId
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "id") Integer postId){
        ModelAndView mav = new ModelAndView("post/add");
        Post post = postService.findPostById(postId);
        mav.addObject("post", post);
        mav.addObject("sections", JSON.toJSON(sectionService.getAllSection()));
        return mav;
    }

    /**
     * 帖子置顶
     * @param postId
     * @return
     */
    @RequestMapping(value = "/top/{id}", method = RequestMethod.GET)
    public String top(@PathVariable(value = "id") Integer postId){
        Post post = postService.findPostById(postId);
        post.setTop(Constants.YES_OR_NO.YES);
        postService.addOrUpdate(post);
        return "redirect:/post/find";
    }

    /**
     * 获得所有版块
     *
     * @return
     */
    @RequestMapping(value = "getSections", method = RequestMethod.GET)
    @ResponseBody
    public ApiJsonResult getSections() {
        return new ApiJsonResult(Constants.JSON_RESULT.OK, sectionService.getAllSection());
    }

    /**
     * 删除一篇帖子, 只提供给管理后台
     * @param postId
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable(value = "id") Integer postId) {
        postService.delete(postId);
        return "redirect:/post/find";
    }

    /**
     * 管理后台创建一个投票帖子
     * @param sectionId
     * @param title
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/createVotePost",method = RequestMethod.GET)
    public String createVotePost(Integer sectionId, String title, Date startTime, Date endTime){

        postService.createVotePost(sectionId,title,startTime,endTime);

        return "redirect:/post/find";
    }


    @RequestMapping(value = "/ckeditor/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile upload) {
        response.setCharacterEncoding("GBK");
        PrintWriter out;
        String callback = request.getParameter("CKEditorFuncNum");
        try {
            if (!upload.isEmpty()) {
                String coverName = FileUtils.generatePictureName(upload);
                String coverPath = TextUtils.getConfig("post_image", this);
                FileUtils.transferFile(coverPath, coverName, upload);
                out = response.getWriter();
                response.reset();
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction("
                        + callback + ",'" + basePath + coverPath+coverName+"',''" + ")"); // 相对路径用于显示图片
                out.println("</script>");
                out.flush();
                out.close();
                return "upload Success";
            }else{
                return "upload Failed";
            }

        } catch (IOException e) {
            return "upload Failed";
        }
    }

}
