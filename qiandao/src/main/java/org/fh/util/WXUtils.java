package org.fh.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.shiro.session.Session;
import org.fh.msg.MyConst;
import org.fh.msg.TokenMsg;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年3月21日 下午9:31:03
 * @explain 类说明 ：微信工具类
 */
public class WXUtils {

	/**
	 * 获取用户openid
	 * 
	 * @param access_token
	 * @return
	 */
	public static String openid(String code) {
		String url = MyConst.OPENID_URL;
		String appid = MyConst.APPID;
		String secret = MyConst.APPSECRET;
		url = url.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
		String openid = HttpUtils.getOpenid(url);
		System.out.println("openid===============================" + openid);
		return openid;
	}

	/**
	 * 获取用户access_token
	 * 
	 * @return
	 */
	public static TokenMsg access_token() {
		// 获取session中token信息
		Session session = Jurisdiction.getSession();
		TokenMsg tokenMsg = (TokenMsg) session.getAttribute(MyConst.ACCESS_TOKEN);
		// 若没有则获取并存缓存
		if (null == tokenMsg) {
			String url = MyConst.ACCESS_TOKEN_URL;
			String appid = MyConst.APPID;
			String secret = MyConst.APPSECRET;
			url = url.replace("APPID", appid).replace("APPSECRET", secret);
			tokenMsg = HttpUtils.getToken(url);
			tokenMsg.setCreate_time(System.currentTimeMillis());
			session.setAttribute(MyConst.ACCESS_TOKEN, tokenMsg);
		} else {
			// 有则判断是否过期
			long time = tokenMsg.getCreate_time() + tokenMsg.getExpires_in() * 1000;
			// 如果过期则重新获取
			if (System.currentTimeMillis() > time) {
				String url = MyConst.ACCESS_TOKEN_URL;
				String appid = MyConst.APPID;
				String secret = MyConst.APPSECRET;
				url = url.replace("APPID", appid).replace("APPSECRET", secret);
				tokenMsg = HttpUtils.getToken(url);
				tokenMsg.setCreate_time(System.currentTimeMillis());
				session.setAttribute(MyConst.ACCESS_TOKEN, tokenMsg);
			}
		}

		System.out.println("access_token==============================" + tokenMsg.getAccess_token());
		return tokenMsg;
	}

	/**
	 * 
	 * @param scene
	 * @param fileName
	 * @return
	 */
	public static String postMiniqrQr(String scene, String fileName) {
		// 项目路径
		String ctxPath = PathUtil.getProjectpath();
		// 存储文件夹
		String bizPath = MyConst.ERCODE_FILE_PATH;
		System.out.println(bizPath);
		// 存储路径
		String ppath = ctxPath + bizPath;
		System.out.println(ppath);
		File file = new File(ppath);
		if (!file.exists()) {
			// 创建文件根目录
			file.mkdirs();
		}
		// 存储地址
		String savePath = file.getPath() + "/" + fileName + ".png";
		// 访问地址
		String qrCode = bizPath + fileName + ".png";
		if (qrCode.contains("\\")) {
			qrCode = qrCode.replace("\\", "/");
		}
		try {
			String wxCodeURL = MyConst.GET_ERCODE_URL + access_token().getAccess_token();
			URL url = new URL(wxCodeURL);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			// 提交模式
			httpURLConnection.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			JSONObject paramJson = new JSONObject();
//			paramJson.put("scene", scene); // 你要放的内容
//			paramJson.put("page", MyConst.PROMOTEURL);
//			paramJson.put("width", 430); // 宽度
//			paramJson.put("auto_color", true);

			paramJson.put("scene", scene);
			paramJson.put("width", 430);
			paramJson.put("is_hyaline", true);
			paramJson.put("path", MyConst.PROMOTEURL);
			paramJson.put("auto_color", true);

			printWriter.write(paramJson.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 开始获取数据
			BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
			OutputStream os = new FileOutputStream(new File(savePath));
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1) {
				os.write(arr, 0, len);
				os.flush();
			}
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MyConst.HTTP_PATH + qrCode;
	}

}
