package com.cyberpeace.common.constant;

public final class WebConstant {

	public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	public static final Integer HTTP_200 = 200;

	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static final String TRACE_HEADER = "X-Request-Id";

	public static final String LANGUAGE = "Accept-Language";

	public static final String LANGUAGE_ZH = "zh-CN";

	public static final String LANGUAGE_EN = "en-US";

	/**
	 * mp鉴权参数
	 */
	public static final String CLIENT_ID = "clientId";

	/**
	 * mp鉴权参数
	 */
	public static final String CLIENT_SECRET = "clientSecret";

	public static final String ENCODING = "UTF-8";

	public static final String PASS_THROUGH_BUSINESS_ID = "business_id";

	public static final String PASS_THROUGH_EXTENSION = "extension";

	public static final String PASS_THROUGH_OPERATOR = "operator";

	public static final Integer SCENE_ID_LENGTH = 32;

	/** 外部接口使用*/
	public static final String OJ_HEADER_KEY = "OPT-OJ-KEY";

	/** 外部接口使用*/
	public static final String OJ_HEADER_KEY_VALUE = "Cmycjs1bWUHsKb8r";

	public static final String POST = "POST";

	public static final String JSON_TYPE = "application/json";

	public static final String GET = "GET";

	private WebConstant() {}
}
