package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.Vote;
import com.bbs.mybatis.model.VoteExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface VoteMapper {
    int countByExample(VoteExample example);

    int deleteByExample(VoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Vote record);

    int insertSelective(Vote record);

    List<Vote> selectByExampleWithRowbounds(VoteExample example, RowBounds rowBounds);

    List<Vote> selectByExample(VoteExample example);

    Vote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByExample(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);
}