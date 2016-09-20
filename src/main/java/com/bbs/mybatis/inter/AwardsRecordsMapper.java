package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.AwardsRecords;
import com.bbs.mybatis.model.AwardsRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AwardsRecordsMapper {
    int countByExample(AwardsRecordsExample example);

    int deleteByExample(AwardsRecordsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AwardsRecords record);

    int insertSelective(AwardsRecords record);

    List<AwardsRecords> selectByExampleWithRowbounds(AwardsRecordsExample example, RowBounds rowBounds);

    List<AwardsRecords> selectByExample(AwardsRecordsExample example);

    AwardsRecords selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AwardsRecords record, @Param("example") AwardsRecordsExample example);

    int updateByExample(@Param("record") AwardsRecords record, @Param("example") AwardsRecordsExample example);

    int updateByPrimaryKeySelective(AwardsRecords record);

    int updateByPrimaryKey(AwardsRecords record);
}