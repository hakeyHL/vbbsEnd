package com.bbs.service.impl;

import com.bbs.mybatis.inter.SectionMapper;
import com.bbs.mybatis.model.Section;
import com.bbs.mybatis.model.SectionExample;
import com.bbs.service.ISectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
@Service
public class SectionServiceImpl implements ISectionService {

    @Resource
    SectionMapper sectionMapper;

    @Override
    public void createSection(Section section) {
        sectionMapper.insert(section);
    }

    @Override
    public void deleteSectionById(int id) {
        sectionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Section> getAllSection() {
        SectionExample example = new SectionExample();
        return sectionMapper.selectByExample(example);
    }

    @Override
    public Section getSection(Integer id) {
        return sectionMapper.selectByPrimaryKey(id);
    }
}
