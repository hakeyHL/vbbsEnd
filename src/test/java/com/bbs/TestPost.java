package com.bbs;

import com.bbs.context.Base;
import com.bbs.service.IPostService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lihongde on 2016/9/9 15:42
 */
public class TestPost extends Base {

    @Resource
    private IPostService postService;

    @Test
    public void list(){
        postService.findPagePosts("", 1, 10);
    }
}
