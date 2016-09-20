package com.bbs.controller.api.vo;

import com.bbs.mybatis.model.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HL on 2016/9/10.
 */
public class PostVo {
    private int postId;
    private long voteNum = 0; //已有投票人数
    private String voteDescription; //投票说明
    private int status; //帖子状态
    private String content;//帖子内容
    private String title;//帖子标题
    private String userName;//发帖人
    private String publishTime; //创建时间
    private long browserNum = 0; //浏览量
    private long commentNum = 0;//评论数
    private long thumbNum = 0;//点赞数
    private String section;//板块
    private String avatarUrl;//用户头像地址
    private int votePost = 0;//是否是投票贴  0 非 1是
    private int hasComment = 0;//是否是普通贴且有评论 0非 1 是
    private List<PostCommentVo> comments = new ArrayList<PostCommentVo>();
    private Date postCreateTime;
    private int userId;
    private int postUserId;
    private boolean thisUserLike = false;
    private String sCommentUserNames;
    private String searchPageTime;
    private List<String> images = new ArrayList<String>();
    private Integer voteId;


    public PostVo() {
    }

    public PostVo(Post post) {
        this.title = post.getTitle();
        this.postUserId = post.getUserId();
        this.browserNum = post.getBrowseNum();
        this.commentNum = post.getCommentNum();
        this.content = post.getContent();
        this.publishTime = new SimpleDateFormat("MM-dd HH:mm").format(post.getPublishTime());
        this.status = post.getStatus();
        this.thumbNum = post.getThumbNum();
        this.title = post.getTitle();
        this.userName = post.getUser();
        this.postId = post.getId();
        this.postCreateTime = post.getPublishTime();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getsCommentUserNames() {
        return sCommentUserNames;
    }

    public void setsCommentUserNames(String sCommentUserNames) {
        this.sCommentUserNames = sCommentUserNames;
    }

    public String getSearchPageTime() {
        return searchPageTime;
    }

    public void setSearchPageTime(String searchPageTime) {
        this.searchPageTime = searchPageTime;
    }

    public int getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(int postUserId) {
        this.postUserId = postUserId;
    }

    public boolean isThisUserLike() {
        return thisUserLike;
    }

    public void setThisUserLike(boolean thisUserLike) {
        this.thisUserLike = thisUserLike;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(Date postCreateTime) {
        this.postCreateTime = postCreateTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getBrowserNum() {
        return browserNum;
    }

    public void setBrowserNum(long browserNum) {
        this.browserNum = browserNum;
    }

    public long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(long commentNum) {
        this.commentNum = commentNum;
    }

    public long getThumbNum() {
        return thumbNum;
    }

    public void setThumbNum(long thumbNum) {
        this.thumbNum = thumbNum;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getVoteDescription() {
        return voteDescription;
    }

    public void setVoteDescription(String voteDescription) {
        this.voteDescription = voteDescription;
    }

    public long getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(long voteNum) {
        this.voteNum = voteNum;
    }


    public List<PostCommentVo> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentVo> comments) {
        this.comments = comments;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getVotePost() {
        return votePost;
    }

    public void setVotePost(int votePost) {
        this.votePost = votePost;
    }

    public int getHasComment() {
        return hasComment;
    }

    public void setHasComment(int hasComment) {
        this.hasComment = hasComment;
    }
}
