package org.fh.msg;

import java.io.Serializable;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年3月21日 下午9:47:39
 * @explain 类说明 ：
 */
public class TokenMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String access_token;
	private int expires_in;
	private long create_time;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

}
