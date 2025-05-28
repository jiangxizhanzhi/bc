/**
 * 这段代码定义了一个名为 `WebConstant` 的公共接口，用于存储与 Web 应用相关的常量值。以下是代码的简要分析： 
 * 1. **命名规范**：该接口遵循了良好的命名规范，名称为 `WebConstant`，清晰地表明其用途是存放与 Web 相关的常量。 
 * 2. **常量定义**： 
 * - 包含多种类型的常量，如字符串 (`String`) 和整数 (`Integer`)。 
 * - 每个常量都有详细的注释说明其用途，例如 `CONTENT_TYPE` 表示内容类型，`AUTHORIZATION_HEADER` 表示授权标头等。 
 * 3. **功能分类**： 
 * - **HTTP 状态码**：如 `HTTP_200`。 
 * - **请求头**：如 `AUTHORIZATION_HEADER`, `TRACE_HEADER`。 
 * - **语言相关**：如 `LANGUAGE`, `LANGUAGE_ZH`, `LANGUAGE_EN`。 
 * - **安全性相关**：如 `CLIENT_ID`, `CLIENT_SECRET`。 
 * - **编码相关**：如 `ENCODING`。 
 * - **场景相关**：如 `SCENE_ID_LENGTH`。 
 * - **外部接口相关**：如 `OJ_HEADER_KEY`, `OJ_HEADER_KEY_VALUE`。 
 * 4. **可维护性**：通过将所有常量集中在一个接口中，便于管理和修改，避免了在多个地方重复定义相同的值。 
 * 5. **国际化支持**：提供了多种语言的支持（如中文和英文），有助于实现多语言环境下的应用。 
 * 总体而言，这段代码结构清晰，注释详尽，适合用作 Web 应用中的常量管理工具。 
**/
package com.cyberpeace.common.constant;

/**
 * Web 常量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface WebConstant {

	/**
	 * 内容类型
	 *
	 * @see String
	 */
	String CONTENT_TYPE = "application/json;charset=UTF-8";

	/**
	 * http 200 （中文）
	 *
	 * @see Integer
	 */
	Integer HTTP_200 = 200;

	/**
	 * Authorization 标头
	 *
	 * @see String
	 */
	String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * 跟踪标头
	 *
	 * @see String
	 */
	String TRACE_HEADER = "X-Request-Id";

	/**
	 * 语言
	 *
	 * @see String
	 */
	String LANGUAGE = "Accept-Language";

	/**
	 * 语言 zh
	 *
	 * @see String
	 */
	String LANGUAGE_ZH = "zh-CN";

	/**
	 * 语言英文
	 *
	 * @see String
	 */
	String LANGUAGE_EN = "en-US";

	/**
	 * mp鉴权参数
	 *
	 * @see String
	 */
	String CLIENT_ID = "clientId";

	/**
	 * mp鉴权参数
	 *
	 * @see String
	 */
	String CLIENT_SECRET = "clientSecret";

	/**
	 * 编码
	 *
	 * @see String
	 */
	String ENCODING = "UTF-8";

	/**
	 * 通过通过业务id
	 *
	 * @see String
	 */
	String PASS_THROUGH_BUSINESS_ID = "business_id";

	/**
	 * 直通扩展
	 *
	 * @see String
	 */
	String PASS_THROUGH_EXTENSION = "extension";

	/**
	 * 直通运算符
	 *
	 * @see String
	 */
	String PASS_THROUGH_OPERATOR = "operator";

	/**
	 * 场景id长度
	 *
	 * @see Integer
	 */
	Integer SCENE_ID_LENGTH = 32;

	/**
	 * 外部接口使用
	 *
	 * @see String
	 */
	String OJ_HEADER_KEY = "OPT-OJ-KEY";

	/**
	 * 外部接口使用
	 *
	 * @see String
	 */
	String OJ_HEADER_KEY_VALUE = "Cmycjs1bWUHsKb8r";
}
