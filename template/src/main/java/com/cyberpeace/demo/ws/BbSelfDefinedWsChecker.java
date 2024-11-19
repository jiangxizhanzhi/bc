package com.cyberpeace.demo.ws;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.component.SelfDefinedWsChecker;

/**
 * BB 自定义 WS 检查器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SelfDefinedWsChecker
 * @since 1.0.0
 */
@Component
public class BbSelfDefinedWsChecker extends SelfDefinedWsChecker {

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
		String adminId = "3f655d87112f4ae094bbaf70830e20aa";

		if (!StringUtils.equals(wsPath, "/ws")) {
			return true;
		}

		// 只允许admin用户建立连接
		return StringUtils.equals(adminId, userId);
	}
}
