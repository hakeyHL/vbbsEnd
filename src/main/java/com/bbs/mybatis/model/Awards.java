package com.bbs.mybatis.model;

import java.math.BigDecimal;

public class Awards {
    private Integer id;

    private String awardsName;

    private Integer awardsNum;

    private BigDecimal probability;

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAwardsName() {
        return awardsName;
    }

    public void setAwardsName(String awardsName) {
        this.awardsName = awardsName == null ? null : awardsName.trim();
    }

    public Integer getAwardsNum() {
        return awardsNum;
    }

    public void setAwardsNum(Integer awardsNum) {
        this.awardsNum = awardsNum;
    }
}