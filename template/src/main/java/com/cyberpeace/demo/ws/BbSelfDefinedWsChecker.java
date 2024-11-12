package com.cyberpeace.demo.ws;

import com.cyberpeace.common.component.SelfDefinedWsChecker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * bb自定义ws检查器
 *
 * @author admin
 * @date 2024/11/12
 */
@Component
public class BbSelfDefinedWsChecker extends SelfDefinedWsChecker {

	/**
	 * 进行自定义检查
	 *
	 * @param requestParams 请求参数
	 * @param wsPath        ws路径
	 * @param userId        用户id
	 * @return boolean
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
