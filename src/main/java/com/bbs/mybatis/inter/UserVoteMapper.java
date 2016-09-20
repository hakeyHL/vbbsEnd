package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.UserVote;
import com.bbs.mybatis.model.UserVoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserVoteMapper {
    int countByExample(UserVoteExample example);

    int deleteByExample(UserVoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserVote record);

    int insertSelective(UserVote record);

    List<UserVote> selectByExampleWithRowbounds(UserVoteExample example, RowBounds rowBounds);

    List<UserVote> selectByExample(UserVoteExample example);

    UserVote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserVote record, @Param("example") UserVoteExample example);

    int updateByExample(@Param("record") UserVote record, @Param("example") UserVoteExample example);

    int updateByPrimaryKeySelective(UserVote record);

    int updateByPrimaryKey(UserVote record);
}