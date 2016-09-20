package com.bbs.service;

import com.bbs.mybatis.model.User;
import com.bbs.mybatis.model.UserThumbPost;

import java.util.List;

/**
 * 操作用户
 *
 * @author Enhon
 */
public interface IUserService {

	void setNameUtf8mb4();
    /**
     * 添加用户方法
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 添加一条点赞记录
     * @param userThumbPost
     */
    void addUserThumbToPost(UserThumbPost userThumbPost);


    void deleteUserThumbPost(UserThumbPost userThumbPost);
	
	
    User findUserByOpenid(String opendid);

    User findUserById(Integer userId);

    List<User> findAllUsers();
}
