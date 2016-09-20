package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.PostImage;
import com.bbs.mybatis.model.PostImageExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PostImageMapper {
    int countByExample(PostImageExample example);

    int deleteByExample(PostImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PostImage record);

    int insertSelective(PostImage record);

    List<PostImage> selectByExampleWithRowbounds(PostImageExample example, RowBounds rowBounds);

    List<PostImage> selectByExample(PostImageExample example);

    PostImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PostImage record, @Param("example") PostImageExample example);

    int updateByExample(@Param("record") PostImage record, @Param("example") PostImageExample example);

    int updateByPrimaryKeySelective(PostImage record);

    int updateByPrimaryKey(PostImage record);
}