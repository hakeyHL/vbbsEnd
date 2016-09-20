package com.bbs;

import com.bbs.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lihongde on 2016/9/14 15:56
 */
public class Test {

    public  static void main(String[] args){
        String ss ="<p>您好,<img alt=\"\" src=\"http://114.215.149.92:80/opt/vbbs/post/5b508452-281c-4882-b6ec-59bab30d0d51.png\" style=\"width: 252px; height: 121px;\" />&nbsp;哈哈&nbsp;<img alt=\"\" src=\"http://114.215.149.92:80/opt/vbbs/post/b0809d7f-5e20-43ec-a6b2-63223865ad73.png\" style=\"width: 1920px; height: 1080px;\" /></p>";
       /* int count = TextUtils.findStrCount(ss, "src");

        int index = ss.indexOf("src");
        List<String> imgList = new ArrayList<String>();
        if(index > 0){
            ss = ss.substring(index);
            System.out.println(ss);
        }*/

      /*  Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        Matcher m = p.matcher(ss);
        System.out.println(m.find());
        System.out.println(m.groupCount());
        while(m.find()){
            //System.out.println(m.group()+"-------------↓↓↓↓↓↓");
            System.out.println(m.group(1));
        }
*/
        List<String> list = TextUtils.getImgSrc(ss);
        for(String s : list){
            System.out.println(s);
        }

     }
}
