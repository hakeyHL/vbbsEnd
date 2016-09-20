package com.bbs.service.impl;

import com.bbs.mybatis.inter.PostCommentMapper;
import com.bbs.mybatis.inter.PostImageMapper;
import com.bbs.mybatis.inter.PostMapper;
import com.bbs.mybatis.model.*;
import com.bbs.service.IPostService;
import com.bbs.util.Constants;
import com.bbs.util.page.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
@Service
public class PostServiceImpl implements IPostService {

    @Resource
    PostMapper postMapper;
    @Resource
    PostCommentMapper postCommentMapper;

    @Resource
    private PostImageMapper postImageMapper;

    @Override
    public Post findPostById(Integer id) {
        return postMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<Post> findPagePosts(String section, int page, int size) {
        PostExample example = new PostExample();
        PostExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Constants.YES_OR_NO.NO);
        if (StringUtils.isNotEmpty(section)) {
            criteria.andSectionIdEqualTo(Integer.parseInt(section));
        }
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        List<Post> list = postMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
        return new Page<Post>(page, size, postMapper.countByExample(example), list);
    }

    @Override
    public void addOrUpdate(Post post) {
        Integer id = post.getId();
        if (id == null) {
            postMapper.insertSelective(post);
        } else {
            postMapper.updateByPrimaryKeyWithBLOBs(post);
        }
    }

    @Override
    public void createVotePost(Integer sectionId, String title, Date startTime, Date endTime) {

        Post post = new Post();

        post.setSectionId(sectionId);
        post.setTitle(title);
        post.setPublishTime(startTime);
        post.setExpireTime(endTime);

        post.setIsApprove(0);//未审批
        post.setType(1);//投票贴子

        postMapper.insertSelective(post);
    }

    @Override
    public void delete(Integer postId) {
        Post post = this.findPostById(postId);
        post.setIsDelete(Constants.YES_OR_NO.YES);
        this.addOrUpdate(post);
    }

    @Override
    public List<Post> findVotePosts() {
        PostExample example = new PostExample();

        PostExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(Constants.POST_TYPE.VOTE_POST);
        criteria.andIsDeleteEqualTo(Constants.YES_OR_NO.NO);

        //设置排序字段
        example.setOrderByClause("publish_time");

        return postMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public void addPostImage(PostImage postImage) {
        postImageMapper.insert(postImage);
    }

}
