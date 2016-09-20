package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.UserThumbPost;
import com.bbs.mybatis.model.UserThumbPostExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserThumbPostMapper {
    int countByExample(UserThumbPostExample example);

    int deleteByExample(UserThumbPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserThumbPost record);

    int insertSelective(UserThumbPost record);

    List<UserThumbPost> selectByExampleWithRowbounds(UserThumbPostExample example, RowBounds rowBounds);

    List<UserThumbPost> selectByExample(UserThumbPostExample example);

    UserThumbPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserThumbPost record, @Param("example") UserThumbPostExample example);

    int updateByExample(@Param("record") UserThumbPost record, @Param("example") UserThumbPostExample example);

    int updateByPrimaryKeySelective(UserThumbPost record);

    int updateByPrimaryKey(UserThumbPost record);
}