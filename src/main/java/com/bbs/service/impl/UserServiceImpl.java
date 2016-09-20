package com.bbs.service.impl;

import com.bbs.mybatis.inter.UserMapper;
import com.bbs.mybatis.inter.UserThumbPostMapper;
import com.bbs.mybatis.model.*;
import com.bbs.mybatis.model.UserExample.Criteria;
import com.bbs.service.IUserService;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserThumbPostMapper userThumbPostMapper;

    @Override
    public void addUser(User user) {
        this.userMapper.insert(user);
    }

    @Override
    public void addUserThumbToPost(UserThumbPost userThumbPost) {
        userThumbPostMapper.insert(userThumbPost);
    }

    @Override
    public void deleteUserThumbPost(UserThumbPost userThumbPost) {
        UserThumbPostExample userThumbPostExample = new UserThumbPostExample();

        userThumbPostExample.createCriteria().andPostidEqualTo(userThumbPost.getPostid());
        userThumbPostExample.createCriteria().andUseridEqualTo(userThumbPost.getUserid());

        userThumbPostMapper.deleteByExample(userThumbPostExample);
    }

	@Override
	public User findUserByOpenid(String opendid) {
		if(StringUtils.isNotBlank(opendid)){
			UserExample example=new UserExample();
			Criteria criteria = example.createCriteria();
			criteria.andOpenidEqualTo(opendid);
			List<User> list = this.userMapper.selectByExample(example);
			if(list.size()==1)
			{
				return list.get(0);
			}else{
				return null;
			}
			
		}
		return null;
		
	}

    @Override
    public User findUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }


	@Override
	public void setNameUtf8mb4() {
		this.userMapper.setNameUtf8mb4();
		
	}

    @Override
    public List<User> findAllUsers() {
        UserExample example = new UserExample();

        return userMapper.selectByExample(example);
    }

}
