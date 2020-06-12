package org.fh.msg;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年2月4日 下午12:25:13
 * @explain 类说明 ：我的常量
 */
public class MyConst {
	/** 返回错误状态码 */
	public static final int ERR_TYPE = -1;
	/** 返回正确状态码 */
	public static final int SUCC_TYPE = 0;
	/** 返回错误信息 */
	public static final String ERR_MSG = "请求失败！";
	/** 返回正确信息 */
	public static final String SUCC_MSG = "请求成功！";

	/** http地址 */
	public static final String HTTP_PATH = "https://www.hemingbi.com/qiandao/";
//	public static final String HTTP_PATH = "http://127.0.0.1:8081/";
	/** 二维码存储地址 */
	public static final String ERCODE_FILE_PATH = "uploadFiles/erCode/";

	/** 默认背景图片 */
	public static final String DEFAULT_BUIMG_URL = HTTP_PATH + "uploadFiles/imgs/bj.jpg";
	/** 默认小程序二维码 */
	public static final String ERCODE_URL = HTTP_PATH + "uploadFiles/imgs/x.jpg";

	/** 小程序AppID */
	public static final String APPID = "wx871a054a66d0071b";
	/** 小程序AppSecret */
	public static final String APPSECRET = "b4fecf44867b4aa4d14c7e8363274a99";
	/** 获取access_token地址 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	/** 获取二维码地址 */
	public static final String GET_ERCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
	/** 小程序access_token缓存 */
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	/** 小程序码跳转地址 */
	public static final String PROMOTEURL = "pages/dainji/index";
	/** openid获取地址 */
	public static final String OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=CODE&grant_type=authorization_code";
	/** 管理员ID */
	public static final String MANAGER_ROLE_ID = "8f2f8fd11ccd431a84591c6d7aa88a8f";
	/** 系统管理员 */
	public static final String ADMIN_ROLE_ID = "1";
}
