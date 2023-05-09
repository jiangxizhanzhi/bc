package com.cyberpeace.demo.ws;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.component.SelfDefinedWsChecker;

/**
 * 自我定义ws检查程序
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Component
public class BbSelfDefinedWsChecker extends SelfDefinedWsChecker {

	@Override
	public boolean doSelfDefinedCheck(Map<String, String> requestParams, String wsPath,
			String userId) {
		String adminId = "3f655d87112f4ae094bbaf70830e20aa";

		if (!StringUtils.equals(wsPath, "/ws")) {
			return true;
		}

		// 只允许admin用户建立连接
		return StringUtils.equals(adminId, userId);
	}
}
