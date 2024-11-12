package com.cyberpeace.demo.ws;

import com.cyberpeace.common.component.SelfDefinedWsChecker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * aa自定义ws检查器
 *
 * @author admin
 * @date 2024/11/12
 */
@Component
public class AaSelfDefinedWsChecker extends SelfDefinedWsChecker {

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
		// 针对课程类的ws连接进行校验
		if (!StringUtils.contains(wsPath, "course")) {
			return true;
		}

		String blackWord = "yqr";

		// 对于路径中的黑名单字段进行校验
		return !StringUtils.contains(wsPath, blackWord);
	}
}
