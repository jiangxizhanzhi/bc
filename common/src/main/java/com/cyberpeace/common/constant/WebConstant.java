package com.cyberpeace.common.constant;

import com.cyberpeace.common.util.ConfigUtil;

/**
 * 网络常量
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface WebConstant {

	/**
	 * 内容类型
	 */
	String CONTENT_TYPE = "application/json;charset=UTF-8";

	String CONTENT_TYPE_NAME = "Content-Type";

	Integer HTTP_200 = 200;

	/**
	 * 授权头
	 */
	String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * 跟踪头
	 */
	String TRACE_HEADER = "X-Request-Id";

	String LANGUAGE = "Accept-Language";

	String LANGUAGE_ZH = "zh-CN";

	String LANGUAGE_EN = "en-US";

	/**
	 * mp鉴权参数
	 */
	String CLIENT_ID = "clientId";

	/**
	 * mp鉴权参数
	 */
	String CLIENT_SECRET = "clientSecret";

	String ENCODING = "UTF-8";

	/**
	 * 通过业务标识
	 */
	String PASS_THROUGH_BUSINESS_ID = "business_id";

	/**
	 * 通过扩展
	 */
	String PASS_THROUGH_EXTENSION = "extension";

	/**
	 * 通过操作符
	 */
	String PASS_THROUGH_OPERATOR = "operator";

	/**
	 * 现场id长度
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

	/**
	 * 令牌
	 */
	String TOKEN = "token";

	/**
	 * yw令牌
	 */
	String YW_TOKEN = ConfigUtil.getContextProperty("yw.token");
}
