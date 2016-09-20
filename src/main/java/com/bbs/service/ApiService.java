package com.bbs.service;

import com.bbs.controller.api.vo.PostVo;
import com.bbs.controller.api.vo.ResponseResultVo;
import com.bbs.mybatis.model.Post;
import com.bbs.mybatis.model.Section;

import java.util.List;

/**
 * Created by HL on 2016/9/10.
 * 在此暂时定义胡礼和武宁要做的功能,
 * 实际开发时再归入不同的service中
 */

public interface ApiService {
    PostVo getPostInfo(Post post, int commentNum, int sort);

    PostVo getPostInfo(Post post, int commentNum, int sort, int userId);
    List<Section> getSectionList();

    List<Post> getPostListTop(int top,int sectionId,int cream);

    void savePostImage(String user_avatar_url, Integer id);

    ResponseResultVo  thumbPost(int id,int userId);

    Post getPostById(Integer postId);

    int getTotalPostNumber();

    int getTotalUsers();

    int getTotalBrowserNum();

    List<PostVo> searchPostByTitle(String content);

    void addPostBrowserNum(int postId);
}
