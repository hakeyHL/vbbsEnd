package com.bbs.service.impl;

import com.bbs.mybatis.inter.AwardsMapper;
import com.bbs.mybatis.inter.AwardsRecordsMapper;
import com.bbs.mybatis.model.Awards;
import com.bbs.mybatis.model.AwardsExample;
import com.bbs.mybatis.model.AwardsRecords;
import com.bbs.mybatis.model.AwardsRecordsExample;
import com.bbs.service.IEggService;
import com.bbs.util.page.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lihongde on 2016/9/13 11:13
 */
@Service
public class EggServiceImpl implements IEggService {

    @Resource
    private AwardsMapper awardsMapper;

    @Resource
    private AwardsRecordsMapper awardsRecordsMapper;

    @Override
    public List<Awards> findAwards() {
        AwardsExample example = new AwardsExample();
        return awardsMapper.selectByExample(example);
    }

    @Override
    public Awards getAwards(Integer id) {
        return awardsMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<Awards> findPageAwards(int page, int size) {
        AwardsExample example = new AwardsExample();
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        List<Awards> list = awardsMapper.selectByExampleWithRowbounds(example, rowBounds);
        return new Page<Awards>(page, size, awardsMapper.countByExample(example), list);
    }

    @Override
    public void addOrUpdate(Awards awards) {
        Integer id = awards.getId();
        if(id == null){
            awardsMapper.insertSelective(awards);
        }else{
            awardsMapper.updateByPrimaryKey(awards);
        }
    }

    @Override
    public void delete(Integer id) {
        awardsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteRecordByAwardId(Integer awardId) {
        AwardsRecordsExample example = new AwardsRecordsExample();
        AwardsRecordsExample.Criteria criteria = example.createCriteria();
        criteria.andAwardIdEqualTo(awardId);
        awardsRecordsMapper.deleteByExample(example);
    }

    @Override
    public Page<AwardsRecords> findPageAwardsRecords(int page, int size) {
        AwardsRecordsExample example = new AwardsRecordsExample();
        example.setOrderByClause("create_time desc");
        RowBounds rowBounds = new RowBounds((page -1) * size, size);
        List<AwardsRecords> list = awardsRecordsMapper.selectByExampleWithRowbounds(example, rowBounds);
        return new Page<AwardsRecords>(page, size, awardsRecordsMapper.countByExample(example), list);
    }

    @Override
    public void addAwardsRecord(AwardsRecords records) {
        awardsRecordsMapper.insert(records);
    }
}
