package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.VoteCandidate;
import com.bbs.mybatis.model.VoteCandidateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface VoteCandidateMapper {
    int countByExample(VoteCandidateExample example);

    int deleteByExample(VoteCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteCandidate record);

    int insertSelective(VoteCandidate record);

    List<VoteCandidate> selectByExampleWithRowbounds(VoteCandidateExample example, RowBounds rowBounds);

    List<VoteCandidate> selectByExample(VoteCandidateExample example);

    VoteCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteCandidate record, @Param("example") VoteCandidateExample example);

    int updateByExample(@Param("record") VoteCandidate record, @Param("example") VoteCandidateExample example);

    int updateByPrimaryKeySelective(VoteCandidate record);

    int updateByPrimaryKey(VoteCandidate record);
}