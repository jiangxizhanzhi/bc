package com.cyberpeace.common.constant;

public interface WebConstant {

	String CONTENT_TYPE = "application/json;charset=UTF-8";

	Integer HTTP_200 = 200;

	String AUTHORIZATION_HEADER = "Authorization";

	String TRACE_HEADER = "X-Request-Id";

	String LANGUAGE = "X-Language";

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

	Integer SCENE_ID_LENGTH = 32;
}
