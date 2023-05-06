package com.cyberpeace.common.component;

import java.util.Map;

/**
 * 自定义ws检查程序
 * 自定义ws连接校验器。
 * 红蓝对抗等业务模式中，需要根据用户的角色、判断是否允许用户建立连接。
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public abstract class SelfDefinedWsChecker {

	/**
	 * 自定义检查
	 * 扩展ws权限校验
	 *
	 * @param requestParams 请求ws连接时的业务参数
	 * @param wsPath        请求连接ws的连接地址
	 * @param userId        请求连接ws的用户id
	 * @return 校验成功返回true
	 */
	public abstract boolean doSelfDefinedCheck(Map<String, String> requestParams, String wsPath,
			String userId);
}
