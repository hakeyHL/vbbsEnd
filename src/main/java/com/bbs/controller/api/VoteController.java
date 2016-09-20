package com.bbs.controller.api;

import com.bbs.controller.BaseController;
import com.bbs.mybatis.model.*;
import com.bbs.util.ApiJsonResult;
import com.bbs.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by HL&WN on 2016/9/10.
 */
@Controller
@RequestMapping("/vote")
public class VoteController extends BaseController{

    /**
     * 返回所有的投票帖子
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public List<Post> getVotePostList() {

        List<Post> votePostList = postService.findVotePosts();

        return votePostList;
    }

    /**
     * 提交投票
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ApiJsonResult vote(Integer voteId, @RequestParam(value = "names[]") String[] names){
        for(String name : names){
            logger.debug("------------提交投票" + "voteId=" + voteId + " name=" + name.trim());
            VoteCandidate candidate = voteService.getCandidate(voteId, name.trim());
            candidate.setVotes(candidate.getVotes() + 1);
            voteService.updateCandidate(candidate);

            User user = (User)session.getAttribute("existUser");
            UserVote userVote = new UserVote();
            userVote.setUserId(user.getId());
            userVote.setVoteId(voteId);
            userVote.setName(name.trim());
            userVote.setVoteTime(new Date());
            voteService.addUserVote(userVote);
        }

        Vote vote = voteService.getVote(voteId);
        vote.setVoteNum(vote.getVoteNum() + 1);
        voteService.addOrUpdateVote(vote);

        logger.debug("-------------------投票成功");

        return new ApiJsonResult(Constants.JSON_RESULT.OK);
    }
}
