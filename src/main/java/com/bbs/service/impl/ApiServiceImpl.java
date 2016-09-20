package com.bbs.service.impl;

import com.bbs.controller.api.vo.PostCommentVo;
import com.bbs.controller.api.vo.PostVo;
import com.bbs.controller.api.vo.ResponseResultVo;
import com.bbs.mybatis.inter.*;
import com.bbs.mybatis.model.*;
import com.bbs.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HL on 2016/9/10.
 */
@Service
public class ApiServiceImpl implements ApiService {
    Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Autowired
    private PostImageMapper postImageMapper;
    @Autowired
    private UserThumbPostMapper userThumbPostMapper;
    @Value("${user_avatar_domain}")
    private String user_avatar_domain;

    public PostVo getPostInfo(Post post, int commentNum, int sort) {
        PostVo postVo = null;
        Post post1 = postMapper.selectByPrimaryKey(post.getId());
        if (post1 == null) {
            return null;
        }
        postVo = new PostVo(post1);
        postVo.setVotePost(0);
        postVo.setHasComment(0);
        //设置浏览量
        setThumNum(postVo);
        Integer sectionId = post1.getSectionId();
        Section section = sectionMapper.selectByPrimaryKey(sectionId);
        if (section != null) {
            //设置版块儿名称
            postVo.setSection(section.getName() == null ? "" : section.getName());
        }
        //讨论帖,只需将帖子的属性返回即可
        User user = userMapper.selectByPrimaryKey(post1.getUserId());
        if (user != null) {
            //如果存在此用户则为其名称和头像赋值,不存在此项为空
            postVo.setUserName(user.getNickName());
            postVo.setAvatarUrl(user.getAvatar());
        }
        //存在此帖子
        Integer type = post1.getType();
        //设置评论
        getComments(postVo, commentNum, sort);
        setPostImagesl(postVo);
        if (type == 0) {
            return postVo;
        } else {
            //投票贴,赋其他属性
            VoteExample voteExample = new VoteExample();
            voteExample.createCriteria().andPostIdEqualTo(post1.getId());
            List<Vote> votes = voteMapper.selectByExample(voteExample);
            if (votes != null && votes.size() > 0) {
                Vote vote = votes.get(0);
                //将vote属性放入到vo中,选项和选项票数
                postVo.setStatus(vote.getStatus());
                postVo.setVoteDescription(vote.getInstructions() == null ? "" : vote.getInstructions());
                postVo.setVoteNum(vote.getVoteNum());
                postVo.setVotePost(1);
            }
        }
        return postVo;
    }

    public PostVo getPostInfo(Post post, int commentNum, int sort, int userId) {
        PostVo postVo = null;
        Post post1 = postMapper.selectByPrimaryKey(post.getId());
        if (post1 == null) {
            return null;
        }
        postVo = new PostVo(post1);
        postVo.setVotePost(0);
        postVo.setHasComment(0);
        //设置浏览量
        setThumNum(postVo, userId);
        Integer sectionId = post1.getSectionId();
        Section section = sectionMapper.selectByPrimaryKey(sectionId);
        if (section != null) {
            //设置版块儿名称
            postVo.setSection(section.getName() == null ? "" : section.getName());
        }
        //讨论帖,只需将帖子的属性返回即可
        User user = userMapper.selectByPrimaryKey(post1.getUserId());
        if (user != null) {
            //如果存在此用户则为其名称和头像赋值,不存在此项为空
            postVo.setUserName(user.getNickName());
            postVo.setAvatarUrl(user.getAvatar());
        }
        //存在此帖子
        Integer type = post1.getType();
        //设置评论
        getComments(postVo, commentNum, sort);
        setPostImagesl(postVo);
        if (type == 0) {
            return postVo;
        } else {
            //投票贴,赋其他属性
            VoteExample voteExample = new VoteExample();
            voteExample.createCriteria().andPostIdEqualTo(post1.getId());
            List<Vote> votes = voteMapper.selectByExample(voteExample);
            if (votes != null && votes.size() > 0) {
                Vote vote = votes.get(0);
                //将vote属性放入到vo中,选项和选项票数
                postVo.setVoteId(vote.getId());
                postVo.setStatus(vote.getStatus());
                postVo.setVoteDescription(vote.getInstructions() == null ? "" : vote.getInstructions());
                postVo.setVoteNum(vote.getVoteNum());
                postVo.setVotePost(1);
            }
        }
        return postVo;
    }

    @Override
    public List<Section> getSectionList() {
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("id asc ");
        sectionExample.createCriteria().andIdIsNotNull();
        return sectionMapper.selectByExample(sectionExample);
    }

    @Override
    public List<Post> getPostListTop(int top, int sectionId, int cream) {
        PostExample postExample = new PostExample();
        postExample.setOrderByClause("publish_time desc ");
        PostExample.Criteria criteria = postExample.createCriteria();
        criteria.andTopEqualTo(top).andIsDeleteEqualTo(0).andIsApproveEqualTo(1);
        //选择sectionId
        if (sectionId != 0) {
            criteria.andSectionIdEqualTo(sectionId);
        }
        //选择精华帖子
        criteria.andStatusEqualTo(cream);

        List<Post> posts = postMapper.selectByExample(postExample);
        return posts;
    }

    @Override
    public void savePostImage(String user_avatar_url, Integer id) {

        PostImage postImage = new PostImage();
        postImage.setImgUrl(user_avatar_url);
        postImage.setPostId(id);
        postImageMapper.insert(postImage);
    }

    @Override
    public ResponseResultVo thumbPost(int id, int userId) {
        ResponseResultVo responseResultVo = new ResponseResultVo();
        responseResultVo.setErrorCode("10001");
        responseResultVo.setMsg("failed");
        responseResultVo.setThumbNumber(0);
        if (id > 0) {
            UserThumbPostExample userThumbPostExample = new UserThumbPostExample();
            userThumbPostExample.createCriteria().andUseridEqualTo(userId).andPostidEqualTo(id);
            List<UserThumbPost> userThumbPosts = userThumbPostMapper.selectByExample(userThumbPostExample);
            if (userThumbPosts != null && userThumbPosts.size() > 0) {
                //已点赞,删除
                int i = userThumbPostMapper.deleteByPrimaryKey(userThumbPosts.get(0).getId());
                responseResultVo.setErrorCode("00000");
                responseResultVo.setMsg("add");
                logger.info("已点赞,取消点赞 ,操作结果 :" + i);
            } else {
                //未点赞,添加
                responseResultVo.setErrorCode("00000");
                responseResultVo.setMsg("cancel");
                UserThumbPost thumbPosts = new UserThumbPost();
                thumbPosts.setPostid(id);
                thumbPosts.setUserid(userId);
                int insert = userThumbPostMapper.insert(thumbPosts);
                logger.info("之前未点赞,执行插入,插入结果是 :" + insert);
            }
        }
        //查询总条数返回
        UserThumbPostExample pExample = new UserThumbPostExample();
        pExample.createCriteria().andPostidEqualTo(id);
        List<UserThumbPost> userThumbs = userThumbPostMapper.selectByExample(pExample);
        if (userThumbs != null && userThumbs.size() > 0) {
            responseResultVo.setThumbNumber(userThumbs.size());
        }
        return responseResultVo;
    }

    @Override
    public Post getPostById(Integer postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    public int getTotalPostNumber() {
//        PostCommentExample postCommentExample=new PostCommentExample();
//        postCommentExample.
        return postMapper.countByExample(new PostExample());
    }

    @Override
    public int getTotalUsers() {
        return userMapper.countByExample(new UserExample());
    }

    @Override
    public int getTotalBrowserNum() {
        PostExample postExample = new PostExample();
        postExample.createCriteria().andBrowseNumIsNotNull();
        int totalNum = 0;
        List<Post> posts = postMapper.selectByExample(postExample);
        for (Post post : posts) {
            totalNum += post.getBrowseNum();
        }
        return totalNum;
    }

    @Override
    public List<PostVo> searchPostByTitle(String content) {
        List<PostVo> postVos = new ArrayList<PostVo>();
        PostExample postExample = new PostExample();
        PostExample.Criteria criteria = postExample.createCriteria();
        criteria.andIdIsNotNull().andIsDeleteEqualTo(0).andIsApproveEqualTo(1);
        //根据title搜索帖子,虽然,没有title....fk!
        if (!content.equals("0")) {
            //不等于0,传了查询条件
            criteria.andTitleLike("%" + content + "%");
        }
        List<Post> posts = postMapper.selectByExample(postExample);
        for (Post post : posts) {
            PostVo postInfo = getPostInfo(post, 4, 0);
            //处理时间和回复人名称
            StringBuilder sb = new StringBuilder();
            for (PostCommentVo commentVo : postInfo.getComments()) {
                if (!sb.toString().contains(commentVo.getNickName())) {
                    sb.append(commentVo.getNickName()).append("、");
                }

            }
            String comString = sb.toString();
            if (comString.contains("、")) {
                comString = comString.substring(0, comString.length() - 1);
            }
            postInfo.setsCommentUserNames(comString);
            postInfo.setSearchPageTime(new SimpleDateFormat("yyyy-MM-dd").format(postInfo.getPostCreateTime()));
            postVos.add(postInfo);
        }
        return postVos;
    }

    @Override
    public void addPostBrowserNum(int postId) {
        Post currentPost = getPostById(postId);
        currentPost.setBrowseNum(currentPost.getBrowseNum() + 1);
        postMapper.updateByPrimaryKey(currentPost);
    }

    public void getComments(PostVo postVo, int commentNum, int sort) {
        PostCommentExample postCommentExample = new PostCommentExample();
        String orderByClause = "create_time desc";
        if (sort == 2) {
            orderByClause = "create_time asc";
        }
        postCommentExample.setOrderByClause(orderByClause);
        PostCommentExample.Criteria criteria = postCommentExample.createCriteria();
        criteria.andPostIdEqualTo(postVo.getPostId());
        if (sort == 1 && postVo.getPostUserId() > 0) {
            //只看楼主
            criteria.andUserIdEqualTo(postVo.getPostUserId());
        }
        List<PostComment> postComments = postCommentMapper.selectByExample(postCommentExample);
        List<PostCommentVo> comments = new ArrayList<PostCommentVo>();
        for (PostComment postComment : postComments) {
            PostCommentVo postCommentVo = new PostCommentVo(postComment);
            postCommentVo.setPublishTime(new SimpleDateFormat("yyyy年MM月dd日").format(postVo.getPostCreateTime()));
            Integer userId = postComment.getUserId();
            User user1 = userMapper.selectByPrimaryKey(userId);
            if (user1 != null) {
                postCommentVo.setAvatarUrl(user1.getAvatar() == null ? "" : user1.getAvatar());
                postCommentVo.setNickName(user1.getNickName() == null ? "" : user1.getNickName());
            }
            comments.add(postCommentVo);
        }
        if (comments != null && comments.size() > 0) {
            postVo.setHasComment(1);
            postVo.setCommentNum(comments.size());
            if (commentNum > 0 && comments.size() > commentNum) {
                //大于指定长度才裁剪
                comments = comments.subList(0, commentNum);
            }
        }
        postVo.setComments(comments);
    }

    public void setThumNum(PostVo postVO) {
        UserThumbPostExample userThumbPostExample = new UserThumbPostExample();
        userThumbPostExample.createCriteria().andPostidEqualTo(postVO.getPostId());
        List<UserThumbPost> userThumbPosts = userThumbPostMapper.selectByExample(userThumbPostExample);
        if (userThumbPosts != null && userThumbPosts.size() > 0) {
            postVO.setThumbNum(userThumbPosts.size());
        }
    }

    public void setThumNum(PostVo postVO, int userId) {
        UserThumbPostExample userThumbPostExample = new UserThumbPostExample();
        userThumbPostExample.createCriteria().andPostidEqualTo(postVO.getPostId());
        List<UserThumbPost> userThumbPosts = userThumbPostMapper.selectByExample(userThumbPostExample);
        if (userThumbPosts != null && userThumbPosts.size() > 0) {
            postVO.setThumbNum(userThumbPosts.size());
        }
        if (userId > 0) {
            for (UserThumbPost userThumbPost : userThumbPosts) {
                if (userThumbPost.getUserid() == userId) {
                    postVO.setThisUserLike(true);
                }
            }
        }
    }

    public void setPostImagesl(PostVo postVO) {
        PostImageExample postImageExample = new PostImageExample();
        postImageExample.createCriteria().andPostIdEqualTo(postVO.getPostId());
        List<PostImage> postImages = postImageMapper.selectByExample(postImageExample);
        for (PostImage postImageL : postImages) {
            postVO.getImages().add(user_avatar_domain + postImageL.getImgUrl());
        }
    }
}
