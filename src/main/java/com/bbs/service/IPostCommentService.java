package com.bbs.service;

import com.bbs.controller.api.vo.ResponseResultVo;
import com.bbs.mybatis.model.PostComment;

/**
 * Created by Administrator on 2016/9/11.
 */
public interface IPostCommentService {

    /**
     * 创建一条帖子评论
     * @param postComment
     */
    ResponseResultVo createPostComment(PostComment postComment, int type);

}
