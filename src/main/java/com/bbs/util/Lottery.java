package com.bbs.util;


import com.bbs.mybatis.model.Awards;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/** 
 * @author lihongde
 * @date 2016-5-29 下午3:25:18 
 */
public class Lottery {
	 // 放大倍数
    private static final int mulriple = 100000;

    /**
     * 抽奖算法
     * <p>根据概率将奖品划分区间，每个区间代表一个奖品，然后抽取随机数，反查落在那个区间上，即为所抽取的奖品。<br>
     * @param prizes
     * @return 奖品id
     */
    public static int lottery(List<Awards> prizes) {
        int lastScope = 0;
        // 洗牌，打乱奖品次序
        Collections.shuffle(prizes);
        Map<Integer, int[]> prizeScopes = new HashMap<Integer, int[]>();
        Map<Integer, Integer> prizeQuantity = new HashMap<Integer, Integer>();
        for (Awards prize : prizes) {
            int prizeId = prize.getId();
            // 划分区间
            int currentScope = lastScope + prize.getProbability().multiply(new BigDecimal(mulriple)).intValue();
            prizeScopes.put(prizeId, new int[] { lastScope + 1, currentScope });
            prizeQuantity.put(prizeId, prize.getAwardsNum());

            lastScope = currentScope;
        }

        // 获取1-1000000之间的一个随机数
        int luckyNumber = new Random().nextInt(mulriple);
        int luckyPrizeId = 0;
        // 查找随机数所在的区间
        if ((null != prizeScopes) && !prizeScopes.isEmpty()) {
            Set<Entry<Integer, int[]>> entrySets = prizeScopes.entrySet();
            for (Entry<Integer, int[]> m : entrySets) {
                int key = m.getKey();
                if (luckyNumber >= m.getValue()[0] && luckyNumber <= m.getValue()[1] && prizeQuantity.get(key) > 0) {
                    luckyPrizeId = key;
                    break;
                }
            }
        }

        if (luckyPrizeId > 0) {
            // 奖品库存减一
        }

        return luckyPrizeId;
    }
}
