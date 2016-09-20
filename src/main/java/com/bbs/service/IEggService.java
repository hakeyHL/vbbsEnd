package com.bbs.service;

import com.bbs.mybatis.model.Awards;
import com.bbs.mybatis.model.AwardsRecords;
import com.bbs.util.page.Page;

import java.util.List;

/**
 * Created by lihongde on 2016/9/13 10:53
 */
public interface IEggService {

    /**
     * 获得所有奖项记录
     * @return
     */
    List<Awards> findAwards();

    Awards getAwards(Integer id);

    Page<Awards> findPageAwards(int page, int size);

    void addOrUpdate(Awards awards);

    void delete(Integer id);

    void deleteRecordByAwardId(Integer awardId);

    Page<AwardsRecords> findPageAwardsRecords(int page, int size);

    void addAwardsRecord(AwardsRecords records);
}
