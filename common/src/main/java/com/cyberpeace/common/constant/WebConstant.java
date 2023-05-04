package com.cyberpeace.common.constant;

import com.cyberpeace.common.util.ConfigUtil;

public interface WebConstant {

	String CONTENT_TYPE = "application/json;charset=UTF-8";

	Integer HTTP_200 = 200;

	String AUTHORIZATION_HEADER = "Authorization";

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

	String PASS_THROUGH_BUSINESS_ID = "business_id";

	String PASS_THROUGH_EXTENSION = "extension";

	String PASS_THROUGH_OPERATOR = "operator";

	Integer SCENE_ID_LENGTH = 32;

	/** 外部接口使用*/
	String OJ_HEADER_KEY = "OPT-OJ-KEY";

	/** 外部接口使用*/
	String OJ_HEADER_KEY_VALUE = "Cmycjs1bWUHsKb8r";

	String TOKEN = "token";

	String YW_TOKEN = ConfigUtil.getContextProperty("yw.token");
}
