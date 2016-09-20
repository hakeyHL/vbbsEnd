package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.PostComment;
import com.bbs.mybatis.model.PostCommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PostCommentMapper {
    int countByExample(PostCommentExample example);

    int deleteByExample(PostCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PostComment record);

    int insertSelective(PostComment record);

    List<PostComment> selectByExampleWithRowbounds(PostCommentExample example, RowBounds rowBounds);

    List<PostComment> selectByExample(PostCommentExample example);

    PostComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PostComment record, @Param("example") PostCommentExample example);

    int updateByExample(@Param("record") PostComment record, @Param("example") PostCommentExample example);

    int updateByPrimaryKeySelective(PostComment record);

    int updateByPrimaryKey(PostComment record);
}