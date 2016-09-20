package com.bbs.mybatis.model;

import java.util.Date;

public class AwardsRecords {
    private Integer id;

    private Integer userId;

    private Integer awardId;

    private String awardsContent;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public String getAwardsContent() {
        return awardsContent;
    }

    public void setAwardsContent(String awardsContent) {
        this.awardsContent = awardsContent == null ? null : awardsContent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}