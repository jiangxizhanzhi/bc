/**
 * 这段代码实现了一个自定义的 WebSocket 检查器 `AaSelfDefinedWsChecker`，继承自 `SelfDefinedWsChecker`。以下是代码的主要功能和逻辑分析： 
 * 1. **功能概述**： 
 * - 该类用于对 WebSocket 连接请求进行自定义检查。 
 * - 主要针对包含“course”路径的 WebSocket 请求进行额外校验，并检查是否包含特定的黑名单字段。 
 * 2. **关键方法**： 
 * - `doSelfDefinedCheck`：重写了父类的方法，实现了具体的检查逻辑。 
 * - 参数： 
 * - `requestParams`：请求参数（未被使用）。 
 * - `wsPath`：WebSocket 的路径。 
 * - `userId`：用户 ID（未被使用）。 
 * - 逻辑： 
 * - 如果 `wsPath` 中不包含 `"course"`，直接返回 `true` 表示通过检查。 
 * - 定义黑名单字段 `"yqr"`。 
 * - 如果 `wsPath` 包含黑名单字段，则返回 `false`；否则返回 `true`。 
 * 3. **技术点**： 
 * - 使用了 Apache Commons Lang 的 `StringUtils` 工具类来判断字符串是否包含特定内容。 
 * - 注解 `@Component` 表明这是一个 Spring Bean，会在 Spring 容器中自动注册。 
 * 4. **适用场景**： 
 * - 适用于需要对 WebSocket 连接进行路径级别检查的场景，特别是涉及课程相关的路径以及黑名单字段的过滤。 
 * 总结：该类通过简单的字符串匹配逻辑，实现了对 WebSocket 连接的路径校验和黑名单过滤，符合设计目标。 
**/
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
