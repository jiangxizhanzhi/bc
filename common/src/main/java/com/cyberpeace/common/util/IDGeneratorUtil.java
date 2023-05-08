package com.cyberpeace.common.util;

import java.util.UUID;

/**
 * idgenerator工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class IDGeneratorUtil {

	/**
	 * idgenerator工具
	 */
	private IDGeneratorUtil() {}

	/**
	 * 得到id
	 *
	 * @return {@link String}
	 */
	public static String getID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
