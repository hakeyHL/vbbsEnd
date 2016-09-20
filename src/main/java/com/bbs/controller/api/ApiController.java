package com.bbs.controller.api;

import com.alibaba.fastjson.JSON;
import com.bbs.controller.BaseController;
import com.bbs.controller.api.vo.PostVo;
import com.bbs.controller.api.vo.ResponseResultVo;
import com.bbs.mybatis.model.*;
import com.bbs.util.HttpClientUtil;
import com.bbs.util.WeixinJSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HL on 2016/9/10.
 */
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Value("${appid}")
    private String appid;
    @Value("${post_image}")
    private String post_image;
    @Value("${downloadweixinimg}")
    private String downloadweixinimg;
    @Value("${user_avatar_url}")
    private String user_avatar_url;

    //1. 发帖
    // 1.1 照片不超过10张

    @RequestMapping("getSections")
    public ModelAndView beforeAddPost(HttpServletRequest request) {
        //TODO 此数据可以Session中
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/microForum/post-card.jsp");
        Object sections = request.getSession().getAttribute("sections");
        if (sections == null) {
            List<Section> sectionList = getSectionList();
            if (sectionList != null && sectionList.size() > 0) {
                request.getSession().setAttribute("sections", sectionList);
            }
        }
        //临时设置头像
        request.getSession().setAttribute("userAvatarUrl", "http://www.iteye.com/upload/logo/user/31849/5527409c-15ab-4a97-b764-977ed2c4704f.jpg");
        return modelAndView;
    }

    public List<Section> getSectionList() {
        List<Section> sectionList = apiService.getSectionList();
        return sectionList;
    }


    @RequestMapping(value = "/createPost", method = RequestMethod.POST)
    public ModelAndView createPost(Post post, String[] weixinserverid) {
        User user = null;
        Object existUser = session.getAttribute("existUser");
        if (existUser != null) {
            user = (User) existUser;
        }
        //先处理一个图片吧
        ModelAndView modelAndView = new ModelAndView("redirect:/api/index");
        //先创建帖子
        if (user != null) {
            post.setPublishTime(new Date());
            post.setTitle(post.getContent());
            post.setUserId(user.getId());
            post.setIsApprove(1);
            post.setIsDelete(0);
            post.setStatus(0);
            post.setTop(0);
            post.setType(0);
            post.setSectionId(post.getSectionId() <= 0 ? 3 : post.getSectionId());
            postService.addOrUpdate(post);
            try {
                //去微信服务器上把图片down到自己服务器上
                if (weixinserverid != null) {

                    for (String string : weixinserverid) {
                        String url = downloadweixinimg.replace("ACCESS_TOKEN", WeixinJSignature.getWeixinAccessToken());
                        url = url.replace("MEDIA_ID", string);
                        String fileName = HttpClientUtil.doGetDownFile(url, new File(post_image));
                        if (post != null) {
                            apiService.savePostImage(user_avatar_url + fileName, post.getId());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return modelAndView;
    }

    /**
     * 用户点赞或者取消赞的方法，前台需要封装postid，和type返回
     *
     * @param id
     */
    @ResponseBody
    @RequestMapping(value = "/thumb", method = RequestMethod.GET)
    public ResponseResultVo likeOrDislike(int id) {
        //先查询用户是否点赞
        Object existUser = session.getAttribute("existUser");
        ResponseResultVo responseResultVo = null;
        if (existUser != null) {
            User user = (User) existUser;
            responseResultVo = apiService.thumbPost(id, user.getId());
        }
        return responseResultVo;
    }

    /**
     * 用户对某个帖子发表评论
     *
     * @param postComment
     */
    @ResponseBody
    @RequestMapping(value = "/addOrDeleteComment")
    public ResponseResultVo addComment(PostComment postComment, HttpServletRequest request, @RequestParam(defaultValue = "1") int type, @RequestParam(defaultValue = "0") int sort) {
        ResponseResultVo responseResultVo = null;
        Object existUser = request.getSession().getAttribute("existUser");
        if (existUser != null) {
            User user = (User) existUser;
            postComment.setUserId(user.getId());
            postComment.setCreateTime(new Date());
            responseResultVo = postCommentService.createPostComment(postComment, type);
            if (responseResultVo.getErrorCode().equals("00000")) {
                //如果成功,将所有回复返回
                Post post = apiService.getPostById(postComment.getPostId());
                PostVo postInfo = apiService.getPostInfo(post, 0, sort);
                //set comments
                responseResultVo.setDataList(postInfo.getComments());
            }
        }
        return responseResultVo;
    }

    /**
     * 获取帖子详情
     *
     * @return
     */
    @RequestMapping("post/info")
    public ModelAndView getVotePostInfo(Post post, @RequestParam(defaultValue = "0") int toVotePage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorCode", "00000");
        modelAndView.addObject("msg", "ok");
        PostVo postVo = null;
        User user = null;
        Object existUser = session.getAttribute("existUser");
        if (existUser != null) {
            user = (User) session.getAttribute("existUser");
            postVo = apiService.getPostInfo(post, 0, 0, user.getId());
        } else {
            postVo = apiService.getPostInfo(post, 0, 0);
        }
        if (postVo == null) {
            modelAndView.addObject("errorCode", "00001");
            modelAndView.addObject("msg", "对不起,无此数据.");
        } else {
            //浏览+1
            apiService.addPostBrowserNum(postVo.getPostId());
            modelAndView.addObject("postVo", postVo);
            if (postVo.getVotePost() == 1 && toVotePage == 1) {
                //查询当前用户是否已经投过票，返回投票的对象
                List<UserVote> userVotes = voteService.findUserVote(user.getId(), postVo.getVoteId());
                logger.info("userId=" + user.getId() + " voteId=" + postVo.getVoteId() + " size=" +userVotes.size() );
                modelAndView.addObject("userVotes", JSON.toJSON(userVotes));
                List<VoteCandidate> candidates = voteService.findCandidateByVoteId(postVo.getVoteId());
                modelAndView.addObject("candidates", candidates);
                //如果是投票贴
                modelAndView.setViewName("forward:/microForum/vote.jsp");
            } else {
                modelAndView.setViewName("forward:/microForum/post-text.jsp");
            }
        }
        return modelAndView;
    }

    @RequestMapping("/index")
    public ModelAndView directToIndex(HttpServletRequest requestc,
                                      @RequestParam(defaultValue = "0", required = false) int sectionId,
                                      @RequestParam(defaultValue = "0", required = false) int cream) {
        ModelAndView modelAndView = new ModelAndView("forward:/microForum/index.jsp");
        this.userService.setNameUtf8mb4();
        //1. 首页需要展示
        List<PostVo> postVos = new ArrayList<PostVo>();
        //置顶帖子列表
        //置顶的帖子显示在最上面 ,还可以点击更多
        List<Post> postsTop = apiService.getPostListTop(1, 0, cream);
        modelAndView.addObject("tops", postsTop);

        //有板块列表,精华tab

        Object sections = request.getSession().getAttribute("sections");
        if (sections == null) {
            List<Section> sectionList = getSectionList();
            if (sectionList != null && sectionList.size() > 0) {
                request.getSession().setAttribute("sections", sectionList);
            }
        }
        modelAndView.addObject("sections", sections);
        modelAndView.addObject("sectionId", sectionId);
        modelAndView.addObject("cream", cream);

        //帖子列表,非置顶
//        帖子显示加上板块
        List<Post> postsNoTop = apiService.getPostListTop(0, sectionId, cream);

        //总帖子数,成员数,浏览量
        for (Post post : postsNoTop) {
//            PostVo postVo = new PostVo(post);
            //首页只展示三个评论
            User user = null;
            Object existUser = session.getAttribute("existUser");
            if (existUser != null) {
                user = (User) session.getAttribute("existUser");
                postVos.add(apiService.getPostInfo(post, 3, 0, user.getId()));
            } else {
                postVos.add(apiService.getPostInfo(post, 3, 0));
            }
        }
        modelAndView.addObject("postVos", postVos);
        // 用户头像,
        Object existUser = requestc.getSession().getAttribute("existUser");
        if (existUser != null) {
            User user = (User) existUser;
            modelAndView.addObject("userAvatarUrl", user.getAvatar());
        }
        //最下面是搜索和发帖
        //总帖子数
        int totalPostNumber = apiService.getTotalPostNumber();
        //用户数
        int totalUsers = apiService.getTotalUsers();
        //浏览数
        int totalBrowserNum = apiService.getTotalBrowserNum();

        modelAndView.addObject("totalPostNumber", totalPostNumber);
        modelAndView.addObject("totalUsers", totalUsers);
        modelAndView.addObject("totalBrowserNum", totalBrowserNum);
        return modelAndView;
    }

    /**
     * 到搜索页之前要准备
     * 总浏览、总人数、总帖子数
     *
     * @return
     */
    @RequestMapping(value = "toSearchPage")
    public ModelAndView forward2SearchPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/microForum/search.jsp");
        int totalBrowserNum = apiService.getTotalBrowserNum();
        int totalPostNumber = apiService.getTotalPostNumber();
        int totalUsers = apiService.getTotalUsers();
        modelAndView.addObject("totalPostNumber", totalPostNumber);
        modelAndView.addObject("totalUsers", totalUsers);
        modelAndView.addObject("totalBrowserNum", totalBrowserNum);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "search")
    public ResponseResultVo searchPost(@RequestParam(defaultValue = "0") String content) {
        ResponseResultVo responseResultVo = new ResponseResultVo();
        responseResultVo.setErrorCode("00000");
        responseResultVo.setMsg("ok");
        try {
            List<PostVo> postVos = apiService.searchPostByTitle(content);
            responseResultVo.setDataList(postVos);
        } catch (Exception e) {
            responseResultVo.setErrorCode("10000");
            responseResultVo.setMsg("fail");
        }
        return responseResultVo;
    }

}
