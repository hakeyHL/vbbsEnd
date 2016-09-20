package com.bbs.service.impl;

import com.bbs.controller.api.vo.ResponseResultVo;
import com.bbs.mybatis.inter.PostCommentMapper;
import com.bbs.mybatis.model.PostComment;
import com.bbs.service.IPostCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created on 2016/9/11.
 */
@Service
public class PostCommentServiceImpl implements IPostCommentService {

    @Resource
    PostCommentMapper postCommentMapper;

    @Override
    public ResponseResultVo createPostComment(PostComment postComment, int type) {
        ResponseResultVo responseResultVo = new ResponseResultVo();
        if (type == 0) {
            //delete
            //先查有没有
            if (postComment.getId() > 0) {
                PostComment postComment1 = postCommentMapper.selectByPrimaryKey(postComment.getId());
                if (postComment1 != null && postComment1.getUserId() == postComment.getUserId()) {
                    int delete = postCommentMapper.deleteByPrimaryKey(postComment.getId());
                    if (delete > 0) {
                        responseResultVo.setErrorCode("00000");
                        responseResultVo.setMsg("ok");
                    }
                }
            }
        } else if (type == 1) {
            //add
            postComment.setId(null);
            int insert = postCommentMapper.insert(postComment);
            if (insert > 0) {
                responseResultVo.setErrorCode("00000");
                responseResultVo.setMsg("ok");
            }
        } else {
            responseResultVo.setErrorCode("00000");
            responseResultVo.setMsg("ok");
        }
        return responseResultVo;
    }
}
