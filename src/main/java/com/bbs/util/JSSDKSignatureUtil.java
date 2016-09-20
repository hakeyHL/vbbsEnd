package com.bbs.util;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import com.bbs.controller.api.vo.JSSDKSignature;
public class JSSDKSignatureUtil {

	private static ResourceBundle re=null;
	static{
		re=ResourceBundle.getBundle("weixin",new Locale("zh", "CN"));
	}
	
	public static JSSDKSignature getJsSdkSignatureVoObj(String url) throws Exception{
		//如果URL中包含#需要进行处理
		int index=url.indexOf("#");
		if(index!=-1){
			url=url.substring(0, index);
		}
		String jsapi_ticket =WeixinJSignature.getWeixinJsapi_token();
		String noncestr=UUID.randomUUID().toString().substring(0, 32);
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp=format.format(new Date());
		Map<String,String> tempMap=new HashMap<String,String>();
		
		tempMap.put("url", url);
		tempMap.put("jsapi_ticket", jsapi_ticket);
		tempMap.put("noncestr", noncestr);
		tempMap.put("timestamp", timestamp);
		
		
		String[] array=new String[]{"url","jsapi_ticket","noncestr","timestamp"};
		Arrays.sort(array);
		StringBuilder strBuilder=new StringBuilder("");
		for (String string : array) {
			strBuilder.append(string+"="+tempMap.get(string)+"&");
		}
		String signature=SHA1Util.getSHA1String(strBuilder.substring(0, strBuilder.length()-1));
		JSSDKSignature jsvo=new JSSDKSignature();
		jsvo.setNoncestr(noncestr);
		jsvo.setSignature(signature);
		jsvo.setTimestamp(timestamp);
		jsvo.setAppid(re.getString("appid"));
		return jsvo;
		
		
	}
}
