package com.cyberpeace.common.component;

import java.util.Map;

/**
 * 自定义 WS 检查器
 *
 * @author SN
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/18
 */
public abstract class SelfDefinedWsChecker {

	/**
	 * 扩展ws权限校验
	 *
	 * @param requestParams 请求ws连接时的业务参数
	 * @param wsPath        请求连接ws的连接地址
	 * @param userId        请求连接ws的用户id
	 * @return 校验成功返回true@see Map<String, String>
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
	public abstract boolean doSelfDefinedCheck(Map<String, String> requestParams, String wsPath,
			String userId);
}
