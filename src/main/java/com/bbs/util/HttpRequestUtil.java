package com.bbs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by lihongde on 2016/9/14 13:37
 */
public class HttpRequestUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static final String CHARACTER = "UTF-8";

    /**
     * @param requestUrl
     * @param message
     */
    public static String httpRequest(String requestUrl, String message) {
        URLConnection conn = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            logger.info("请求数据 ： " + message);
            conn = url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", Integer.toString(message.length()));
            conn.setRequestProperty("Content-Type", "text/xml; charset=" + CHARACTER + "");
            OutputStream ops = null;
            ops = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(ops, CHARACTER);
            osw.write(message);
            osw.flush();
            osw.close();

            //发送成功后，获取服务器的响应xml串：
            String line = "";
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARACTER));
            while ((line = br.readLine()) != null) {
                sb.append(line+ "\r\n");
            }
            logger.info("服务器返回数据 ：" + sb.toString());
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createSignMessageString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    public static byte[] getRandomBytes(int len) {
        Random random = new Random();
        byte[] b = new byte[len];
        random.nextBytes(b);
        return b;
    }

    /**
     * 将二进制转化为16进制字符串
     *
     * @param b 二进制字节数组
     * @return String
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}
