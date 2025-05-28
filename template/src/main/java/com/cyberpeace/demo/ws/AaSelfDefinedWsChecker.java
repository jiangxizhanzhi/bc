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
