package com.bbs.controller.api.vo;

import com.bbs.mybatis.model.PostComment;

/**
 * Created by HL on 2016/9/11.
 */
public class PostCommentVo {
    private int commentId;
    private String content;
    private String nickName;
    private String avatarUrl;
    private String publishTime;

    public PostCommentVo() {
    }

    public PostCommentVo(PostComment postComment) {
        this.commentId = postComment.getId();
        this.content = postComment.getContent();
    }

    public PostCommentVo(int commentId, String content, String nickName, String avatarUrl) {
        this.commentId = commentId;
        this.content = content;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
