package com.cyberpeace.common.constant;

/**
 * Web 常量
 *
 * @author SN
 * @date 2024/11/07
 */
public interface WebConstant {

	/**
	 * 内容类型
	 */
	String CONTENT_TYPE = "application/json;charset=UTF-8";

	/**
	 * http 200 （中文）
	 */
	Integer HTTP_200 = 200;

	/**
	 * Authorization 标头
	 */
	String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * 跟踪标头
	 */
	String TRACE_HEADER = "X-Request-Id";

	/**
	 * 语言
	 */
	String LANGUAGE = "Accept-Language";

	/**
	 * 语言 zh
	 */
	String LANGUAGE_ZH = "zh-CN";

	/**
	 * 语言 EN
	 */
	String LANGUAGE_EN = "en-US";

	/**
	 * mp鉴权参数
	 */
	String CLIENT_ID = "clientId";

	/**
	 * mp鉴权参数
	 */
	String CLIENT_SECRET = "clientSecret";

	/**
	 * 编码
	 */
	String ENCODING = "UTF-8";

	/**
	 * 通过通过业务id
	 */
	String PASS_THROUGH_BUSINESS_ID = "business_id";

	/**
	 * 直通扩展
	 */
	String PASS_THROUGH_EXTENSION = "extension";

	/**
	 * 直通运算符
	 */
	String PASS_THROUGH_OPERATOR = "operator";

	/**
	 * 场景id长度
	 */
	Integer SCENE_ID_LENGTH = 32;

	/**
	 * 外部接口使用
	 */
	String OJ_HEADER_KEY = "OPT-OJ-KEY";

	/**
	 * 外部接口使用
	 */
	String OJ_HEADER_KEY_VALUE = "Cmycjs1bWUHsKb8r";
}
