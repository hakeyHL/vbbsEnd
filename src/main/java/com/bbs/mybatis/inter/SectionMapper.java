package com.bbs.mybatis.inter;

import com.bbs.mybatis.model.Section;
import com.bbs.mybatis.model.SectionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SectionMapper {
    int countByExample(SectionExample example);

    int deleteByExample(SectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Section record);

    int insertSelective(Section record);

    List<Section> selectByExampleWithRowbounds(SectionExample example, RowBounds rowBounds);

    List<Section> selectByExample(SectionExample example);

    Section selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Section record, @Param("example") SectionExample example);

    int updateByExample(@Param("record") Section record, @Param("example") SectionExample example);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);
}