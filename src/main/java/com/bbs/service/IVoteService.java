package com.bbs.service;

import com.bbs.mybatis.model.UserVote;
import com.bbs.mybatis.model.Vote;
import com.bbs.mybatis.model.VoteCandidate;
import com.bbs.util.page.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by lihongde on 2016/9/12 15:43
 */
public interface IVoteService {

    /**
     * 条件查询投票记录
     * @param post
     * @param page
     * @param size
     * @return
     */
    Page<Vote> findPageVotes(String post, int page, int size);


    /**
     * 发起投票
     * @param vote
     */
    void addOrUpdateVote(Vote vote);

    /**
     * 候选人导入
     * @param candidateFile
     * @param voteId
     * @return
     */
    Map<String, Object> importExcelFile(MultipartFile candidateFile, Integer voteId) throws Exception;

    Vote getVote(Integer voteId);

    /**
     * 获得投票帖的候选人
     * @param voteId
     * @return
     */
    List<VoteCandidate> findCandidateByVoteId(Integer voteId);

    VoteCandidate getCandidate(Integer voteId, String name);

    void updateCandidate(VoteCandidate candidate);

    /**
     * 添加用户投票记录
     * @param userVote
     */
    void addUserVote(UserVote userVote);

    /**
     * 查询用户投票记录
     * @param userId
     * @param voteId
     * @return
     */
    List<UserVote> findUserVote(Integer userId, Integer voteId);
}
