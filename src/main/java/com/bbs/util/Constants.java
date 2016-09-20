package com.bbs.util;

/**
 * Created by lihongde on 2016/9/8 11:47
 */
public class Constants {

    /**
     * 默认分页起始页
     */
    public static final Integer DEFAULT_PAGE = 1;
    /**
     * 默认页面大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 25;

    public static final Integer ADMIN_USER_ID = 1;

    /**
     * 服务器返回状态
     * @author lihongde
     *
     */
    public static class JSON_RESULT{
        /**
         * 成功
         */
        public static int OK = 200;

        /**
         * 客户端请求无效
         */
        public static int BAD_REQUEST = 400;

        /**
         * token失效或非法，需要重新登录
         */
        public static int NEED_LOGIN = 401;

        /**
         * 普通失败，message中带原因
         */
        public static int FAILED = 403;

        /**
         * 未找到请求页面
         */
        public static int NOT_FOUND = 404;

        /**
         * 服务器错误
         */
        public static int SERVER_ERROR = 500;

    }

    /**
     * 帖子状态
     */
    public static class POST_STATUS{

        /**
         * 普通
         */
        public static final Integer COMMON = 0;

        /**
         * 精华
         */
        public static final Integer ESSENCE = 1;

    }

    /**
     * 帖子类型
     */
    public static class POST_TYPE{

        /**
         * 讨论帖
         */
        public static final Integer DISCUSS_POST = 0;

        /**
         * 投票帖
         */
        public static final Integer VOTE_POST = 1;

    }

    /**
     * 是否
     */
    public static class YES_OR_NO{

        /**
         * 是
         */
        public static final Integer YES = 1;

        /**
         * 否
         */
        public static final Integer NO = 0;

    }

    /**
     * 投票状态
     */
    public static class VOTE_STATUS{

        /**
         * 正在进行
         */
        public static final Integer VOTE_ING = 0;

        /**
         * 投票结束
         */
        public static final Integer VOTE_FINISH = 1;
    }

}
