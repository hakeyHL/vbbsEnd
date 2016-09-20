package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.Awards;
import com.bbs.mybatis.model.AwardsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AwardsMapper {
    int countByExample(AwardsExample example);

    int deleteByExample(AwardsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Awards record);

    int insertSelective(Awards record);

    List<Awards> selectByExampleWithRowbounds(AwardsExample example, RowBounds rowBounds);

    List<Awards> selectByExample(AwardsExample example);

    Awards selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Awards record, @Param("example") AwardsExample example);

    int updateByExample(@Param("record") Awards record, @Param("example") AwardsExample example);

    int updateByPrimaryKeySelective(Awards record);

    int updateByPrimaryKey(Awards record);
}