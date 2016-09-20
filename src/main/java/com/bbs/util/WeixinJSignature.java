package com.bbs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.bbs.util.MD5Util;
import com.bbs.controller.api.vo.GetJsapiticketVo;
import com.bbs.controller.api.vo.ReturnJsapiticketVo;
import com.bbs.util.SHA1Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class WeixinJSignature {
	private static ResourceBundle  re=null;
	static{
		re=ResourceBundle.getBundle("weixin",new Locale("zh", "CN"));
	}
	
	public static String getJsSdkSignature(String url) throws UnsupportedEncodingException{
		//如果URL中包含#需要进行处理
		int index=url.indexOf("#");
		if(index!=-1){
			url=url.substring(0, index);
		}
		String jsapi_ticket = re.getString("getjsapi_ticket_url");
		String noncestr=UUID.randomUUID().toString().substring(0, 32);
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp=format.format(new Date());
		Map<String,String> tempMap=new HashMap<String,String>();
		tempMap.put("url", URLEncoder.encode(url, "UTF-8"));
		tempMap.put("jsapi_ticket", jsapi_ticket);
		tempMap.put("noncestr", noncestr);
		tempMap.put("timestamp", timestamp);
		
		
		String[] array=new String[]{"url","jsapi_ticket","noncestr","timestamp"};
		Arrays.sort(array);
		StringBuilder strBuilder=new StringBuilder("");
		for (String string : array) {
			strBuilder.append(string+"="+tempMap.get(string)+"&");
		}
		
		return SHA1Util.getSHA1String(strBuilder.substring(0, strBuilder.length()-1));
	}
	
	
	
	public static String getWeixinSignature(Map<String,String> map,String sing_key) throws UnsupportedEncodingException{
		if(map!=null){
		Set<String> keySet = map.keySet();
		StringBuilder strBuilder=new StringBuilder();
		List<String> notNullKey=new ArrayList<String>();
		for (String string : keySet) {
			if(StringUtils.isNotBlank(string)){
				notNullKey.add(string);
			}
		}
		String[] array = notNullKey.toArray(new String[notNullKey.size()]);
		Arrays.sort(array);
		for (String string : array) {
			strBuilder.append(string+"="+map.get(string)+"&");
		}
		strBuilder.append("key="+sing_key);
		String md5String = MD5Util.getMD5String(strBuilder.toString()).toUpperCase();
		return md5String;
		}
		return null;
	}
	
	
	public static String getWeixinJsapi_token() throws Exception{
		GetJsapiticketVo jsapitic=new GetJsapiticketVo();
		Calendar calendat = Calendar.getInstance();
		jsapitic.setAction("get_system_value");
		jsapitic.setCreatetime(new Date().getTime()+"");
		jsapitic.setNonce_str(UUID.randomUUID().toString());
		jsapitic.setRequest_client_id(re.getString("request_client_id"));
		jsapitic.setValueId("JS_API_TICKET");
		//设置签名
		Map<String,String> tempMap=new HashMap<String, String>();
		tempMap.put("request_client_id", jsapitic.getRequest_client_id());
		tempMap.put("nonce_str", jsapitic.getNonce_str());
		tempMap.put("createtime", jsapitic.getCreatetime());
		tempMap.put("action", jsapitic.getAction());
		tempMap.put("valueId", jsapitic.getValueId());
		String sign = getWeixinSignature(tempMap,re.getString("sign_key"));
		jsapitic.setSign(sign);
		XStream stream=new XStream(new XppDriver(new XmlFriendlyReplacer("-_", "_")));
		stream.alias("xml",GetJsapiticketVo.class);
		String xmlData = stream.toXML(jsapitic);
		String res = HttpClientUtil.doPostXmlString(re.getString("getjsapi_ticket_url"), xmlData);
		XStream streamRes=new XStream(new XppDriver(new XmlFriendlyReplacer("-_", "_")));
		streamRes.alias("xml",ReturnJsapiticketVo.class);
		ReturnJsapiticketVo obj = (ReturnJsapiticketVo) streamRes.fromXML(res);
		return obj.getValue();
	}
	public static String getWeixinAccessToken() throws Exception{
		GetJsapiticketVo jsapitic=new GetJsapiticketVo();
		Calendar calendat = Calendar.getInstance();
		jsapitic.setAction("get_system_value");
		jsapitic.setCreatetime(new Date().getTime()+"");
		jsapitic.setNonce_str(UUID.randomUUID().toString());
		jsapitic.setRequest_client_id(re.getString("request_client_id"));
		jsapitic.setValueId("ACCESS_TOKEN");
		//设置签名
		Map<String,String> tempMap=new HashMap<String, String>();
		tempMap.put("request_client_id", jsapitic.getRequest_client_id());
		tempMap.put("nonce_str", jsapitic.getNonce_str());
		tempMap.put("createtime", jsapitic.getCreatetime());
		tempMap.put("action", jsapitic.getAction());
		tempMap.put("valueId", jsapitic.getValueId());
		String sign = getWeixinSignature(tempMap,re.getString("sign_key"));
		jsapitic.setSign(sign);
		XStream stream=new XStream(new XppDriver(new XmlFriendlyReplacer("-_", "_")));
		stream.alias("xml",GetJsapiticketVo.class);
		String xmlData = stream.toXML(jsapitic);
		String res = HttpClientUtil.doPostXmlString(re.getString("getjsapi_ticket_url"), xmlData);
		XStream streamRes=new XStream(new XppDriver(new XmlFriendlyReplacer("-_", "_")));
		streamRes.alias("xml",ReturnJsapiticketVo.class);
		ReturnJsapiticketVo obj = (ReturnJsapiticketVo) streamRes.fromXML(res);
		return obj.getValue();
	}
}
