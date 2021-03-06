package org.fh.util;

import java.util.UUID;

/**
 * 说明：生成UUID(32位不重复的字符串)
 * 作者：FH Admin Q313596790
 * 官网：www.fhadmin.org
 */
public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	public static void main(String[] args) {
		System.out.println(getRamdom());
	}
	
	public static String getRamdom() {
		long num = (long) ((Math.random() * 9 + 1) * 1000000000000000l);
		return String.valueOf(num);
	}
}
