package org.fh.msg;

import java.io.Serializable;

/**
 * @author 作者 BealHe
 * @date 创建时间：2019年12月26日 下午4:26:09
 * @explain 类说明 ：
 */
public class ApiReturnMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 消息类型（0成功、-1失败） */
	private int msgType;
	/** 错误消息 */
	private String errMsg;
	/** 返回正常的数据 */
	private Object returnMsg;

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(Object returnMsg) {
		this.returnMsg = returnMsg;
	}

}
