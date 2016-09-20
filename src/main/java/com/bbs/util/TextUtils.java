package com.bbs.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static final String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
    private static final String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
    private static final String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|＼n";//定义空格回车换行符
	
	/**
	 * 从Properties文件中读取配置项
	 * @param key
	 * @param object
	 * @return
	 */
	public static String getConfig(String key,Object object){
		 InputStream inputStream = object.getClass().getClassLoader().getResourceAsStream("system.properties");
		  Properties p = new Properties();
		  try {
			  p.load(inputStream);
		  } catch (IOException e1) {
			  e1.printStackTrace();
		  }
		  
		return p.getProperty(key);
	}
	/**
	 * 获取纯文本
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){

        Pattern p_script= Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style= Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

        htmlStr = htmlStr.replaceAll("&nbsp;","");
        return htmlStr.trim(); //返回文本字符串 
    }

    /**
     * 获得src属性值
     * @param content
     * @return
     */
    public static List<String> getImgSrc(String content){

        List<String> list = new ArrayList<String>();
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);

                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    list.add(str_src);
                }
                //结束匹配<img />标签中的src

                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        return list;
    }
	
	/**
	 * 获取扩展名
	 * @param fileName
	 * @param defaultExtName
	 * @return
	 */
	public static String getFileExtName(String fileName, String defaultExtName) {
		if (fileName.lastIndexOf(".") == -1) {
			String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
			return prefix;
		}
		return defaultExtName;
	}
	
	/**
	 * 字符集转换
	 * @param text
	 * @return
	 */
	public static String characterConversion(String text){
		if(text != null && !text.equals("")){
			try {
				text = URLDecoder.decode(text.trim().replaceAll("%", "%25"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return text;
	}

    public static String generateRandomString(int length){

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ;i<length;i++){
            int currentLetter = (int) (Math.random() * 10);
            sb.append(currentLetter);
        }
        return sb.toString();
    }

    /**
     *
     * The number of occurrences of find keyword in srcText
     *
     * @param srcText
     * @param keyword
     * @return
     */
    public static int findStrCount(String srcText, String keyword) {
        int count = 0;
        int leng = srcText.length();
        int j = 0;
        for (int i = 0; i < leng; i++) {
            if (srcText.charAt(i) == keyword.charAt(j)) {
                j++;
                if (j == keyword.length()) {
                    count++;
                    j = 0;
                }
            } else {
                i = i - j;// should rollback when not match
                j = 0;
            }
        }

        return count;
    }
}
