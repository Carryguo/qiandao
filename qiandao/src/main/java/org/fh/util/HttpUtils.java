package org.fh.util;

import org.fh.msg.TokenMsg;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年3月18日 下午4:21:00
 * @explain 类说明 ：http工具类
 */
public class HttpUtils {

	/**
	 * 获取公众号tokenGET请求
	 * 
	 * @param url
	 * @return
	 */
	public static TokenMsg getToken(String url) {
		RestTemplate client = new RestTemplate();
		return client.getForObject(url, TokenMsg.class);
	}

	/**
	 * 获取用户openid
	 * 
	 * @param url
	 * @return
	 */
	public static String getOpenid(String url) {
		RestTemplate client = new RestTemplate();
		String jstoken = client.getForObject(url, String.class);
		System.out.println("jstoken===================" + jstoken);
		// 获取到token
		String openid = JSONObject.parseObject(jstoken).getString("openid");
		return openid;
	}

}
