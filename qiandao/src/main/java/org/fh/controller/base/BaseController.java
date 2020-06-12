package org.fh.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.fh.entity.PageData;
import org.fh.msg.ApiReturnMsg;
import org.fh.msg.MyConst;
import org.fh.util.UuidUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 说明：所有处理类父类  
 * 作者：FH Admin Q313596790
 * 官网：www.fhadmin.org
 */
public class BaseController {

	/**
	 * new PageData对象
	 * @return
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}
	
	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}

	/**
	 * 返回成功消息
	 * 
	 * @param obj
	 * @return
	 */
	public ApiReturnMsg returnSuccessMsg(Object obj) {
		ApiReturnMsg msg = new ApiReturnMsg();
		msg.setMsgType(MyConst.SUCC_TYPE);
		msg.setErrMsg(MyConst.SUCC_MSG);
		msg.setReturnMsg(obj);
		return msg;
	}

	/**
	 * 返回成功消息
	 * 
	 * @return
	 */
	public ApiReturnMsg returnSuccessMsg() {
		return returnSuccessMsg(null);
	}

	/**
	 * 返回失败消息
	 * 
	 * @param obj
	 * @return
	 */
	public ApiReturnMsg returnDefaultMsg(Object obj) {
		ApiReturnMsg msg = new ApiReturnMsg();
		msg.setMsgType(MyConst.ERR_TYPE);
		msg.setErrMsg(MyConst.ERR_MSG);
		msg.setReturnMsg(obj);
		return msg;
	}

	/**
	 * 返回失败消息
	 * 
	 * @return
	 */
	public ApiReturnMsg returnDefaultMsg() {
		return returnDefaultMsg(null);
	}
	
}
