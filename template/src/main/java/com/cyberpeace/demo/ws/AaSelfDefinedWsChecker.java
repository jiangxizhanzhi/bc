package com.cyberpeace.demo.ws;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.component.SelfDefinedWsChecker;

/**
 * AA 自定义 WS 检查器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SelfDefinedWsChecker
 * @since 1.0.0
 */
@Component
public class AaSelfDefinedWsChecker extends SelfDefinedWsChecker {

	/**
	 * 执行自定义检查
	 *
	 * @param requestParams 请求参数
	 * @param wsPath        WS 路径
	 * @param userId        用户id
	 * @return boolean
	 * @throws
	 * @see Map<String, String>
	 * @see String
	 * @see String
	 * @see boolean
	 */
	@Override
	public boolean doSelfDefinedCheck(Map<String, String> requestParams, String wsPath,
			String userId) {
		// 针对课程类的ws连接进行校验
		if (!StringUtils.contains(wsPath, "course")) {
			return true;
		}

		String blackWord = "yqr";

		// 对于路径中的黑名单字段进行校验
		return !StringUtils.contains(wsPath, blackWord);
	}
}
