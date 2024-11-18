package com.cyberpeace.common.constant;

/**
 * md 常数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface MdConstant {

	/**
	 * 仅 URL img
	 *
	 * @see String
	 */
	String ONLY_URL_IMG = "![](%s)";

	/**
	 * IMG
	 *
	 * @see String
	 */
	String IMG = "![%s](%s)";

	/**
	 * 仅描述 IMG
	 *
	 * @see String
	 */
	String ONLY_DESCRIPTION_IMG = "![%s]()";

}
