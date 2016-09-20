package com.bbs.service;

import com.bbs.mybatis.model.Post;
import com.bbs.mybatis.model.PostComment;
import com.bbs.mybatis.model.PostImage;
import com.bbs.util.page.Page;

import java.util.Date;
import java.util.List;

/**
 * Created on 2016/9/8.
 */
public interface IPostService {

    /**
     * 根据主键找post
     * @param id
     * @return
     */
    Post findPostById(Integer id);

    /**
     * 根据版块查找帖子
     * @param section
     * @param page
     * @param size
     * @return
     */
    Page<Post> findPagePosts(String section, int page, int size);

    /**
     * 添加或修改帖子
     * @param post
     */
    void addOrUpdate(Post post);

    /**
     * 创建一个投票帖子，只能管理后台调用
     * @param sectionId        板块id
     * @param title           标题
     * @param startTime     开始时间
     * @param endTime       结束时间
     */
    void createVotePost(Integer sectionId,String title,Date startTime,Date endTime);

    /**
     * 删除一篇帖子
     * @param postId
     */
    void delete(Integer postId);

    /**
     * 获得所有投票帖
     * @return
     */
    List<Post> findVotePosts();

    void addPostImage(PostImage postImage);
}
