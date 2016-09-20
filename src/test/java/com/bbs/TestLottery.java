package com.bbs;

import com.bbs.context.Base;
import com.bbs.mybatis.model.Awards;
import com.bbs.service.IEggService;
import com.bbs.util.Lottery;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lihongde on 2016/9/13 11:25
 */
public class TestLottery extends Base{

    @Resource
    private IEggService eggService;

    @Test
    public  void lottery(){
        List<Awards> prizes = eggService.findAwards();

        for(Awards award : prizes){
            award.setProbability(new BigDecimal((float)award.getAwardsNum() / prizes.size()));
        }

        int id = Lottery.lottery(prizes);
        System.out.println("中奖id= " + id);

    }
}
