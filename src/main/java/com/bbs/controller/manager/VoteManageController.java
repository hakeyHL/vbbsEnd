package com.bbs.controller.manager;

import com.alibaba.fastjson.JSON;
import com.bbs.controller.BaseController;
import com.bbs.mybatis.model.Post;
import com.bbs.mybatis.model.Vote;
import com.bbs.service.IPostService;
import com.bbs.service.IVoteService;
import com.bbs.util.ApiJsonResult;
import com.bbs.util.Constants;
import com.bbs.util.page.Page;
import com.bbs.util.page.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihongde on 2016/9/12 15:09
 */
@Controller
@RequestMapping(value = "/vote")
public class VoteManageController extends BaseController {

    @Resource
    private IVoteService voteService;

    @Resource
    private IPostService postService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listVotes() {
        return "redirect:/vote/find";
    }

    /**
     * 条件查询投票列表
     * @param post
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView findVotes(String post) {
        ModelAndView mav = new ModelAndView("/vote/list");
        Page<Vote> pages = voteService.findPageVotes(post, page, size);
        mav.addObject("page", PageHelper.getPageModel(request, pages));
        List<Vote> voteList = new ArrayList<Vote>();
        for (Vote vote : pages.getItems()) {
            vote.setPost(postService.findPostById(vote.getPostId()).getTitle());
            voteList.add(vote);
        }
        mav.addObject("votes", voteList);
        return mav;
    }

    /**
     * 发起投票
     * @return
     */
    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    public ModelAndView candidate(){
        ModelAndView mav = new ModelAndView("vote/add");
        List<Post> votePosts = postService.findVotePosts();
        mav.addObject("votePosts", JSON.toJSON(votePosts));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiJsonResult addVote(Vote vote, MultipartFile candidateFile){
        vote.setStatus(Constants.VOTE_STATUS.VOTE_ING);
        voteService.addOrUpdateVote(vote);

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result = voteService.importExcelFile(candidateFile, vote.getId());
            result.put("success", true);
        } catch (Exception e) {
            logger.error("导入候选人失败");
            result.put("success", false);
            e.printStackTrace();
        }

        return new ApiJsonResult(Constants.JSON_RESULT.OK, result);
    }

    /**
     * 获得所有投票帖
     * @return
     */
    @RequestMapping(value = "/getVotePosts", method = RequestMethod.GET)
    @ResponseBody
    public ApiJsonResult getVotePosts(){
        return new ApiJsonResult(Constants.JSON_RESULT.OK, postService.findVotePosts());
    }

    /**
     * 停止投票
     * @return
     */
    @RequestMapping(value = "/stop/{id}", method = RequestMethod.GET)
    public String stop(@PathVariable(value = "id") Integer voteId){
        Vote vote = voteService.getVote(voteId);
        vote.setStatus(Constants.VOTE_STATUS.VOTE_FINISH);
        voteService.addOrUpdateVote(vote);
        return "redirect:/vote/find";
    }
}
