package com.bbs;

import com.bbs.context.Base;
import com.bbs.util.HttpRequestUtil;
import com.bbs.util.TextUtils;
import org.junit.*;

/**
 * Created by lihongde on 2016/9/19 18:03
 */
public class Lottory extends Base {

    @org.junit.Test
    public void test(){

        String xml = "<xml><request_client_id>12345678</request_client_id><nonce_str>6A42692BD89480C9F08F8D15BD8D91DC</nonce_str><sign>9876A1E834FB9838AFAC6BAE9277ED6B</sign><action>lottery_gift</action><createtime>1474279174</createtime><openid>oc7yYwZ_JdwG35eglx0aiORFn1Yw</openid><card_type>481</card_type><quantity>1</quantity></xml> ";
        String result = HttpRequestUtil.httpRequest(TextUtils.getConfig("request_url", this), xml);
        logger.info("返回数据 ： " + result);

    }
}
