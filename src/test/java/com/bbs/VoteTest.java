package com.bbs;

import com.bbs.context.Base;
import com.bbs.mybatis.model.UserVote;
import com.bbs.service.IVoteService;
import org.junit.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lihongde on 2016/9/19 10:08
 */
public class VoteTest extends Base {

    @Resource
    private IVoteService voteService;

    @org.junit.Test
    public void userVote(){
        List<UserVote> userVotes = voteService.findUserVote(15, 16);
        for(UserVote userVote : userVotes){
            logger.info("name=" + userVote.getName());
        }
    }
}
