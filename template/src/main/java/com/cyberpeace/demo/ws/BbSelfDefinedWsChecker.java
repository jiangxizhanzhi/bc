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
 * @date 2025/05/28
 * @see SelfDefinedWsChecker
 * @since 1.0.0
 */
/**
 * 这段代码是一个自定义的 WebSocket 检查器类 `BbSelfDefinedWsChecker`，继承自 `SelfDefinedWsChecker`。以下是代码的主要功能和逻辑分析： 
 * 1. **类的功能**： 
 * - 该类用于实现自定义的 WebSocket 连接检查逻辑。 
 * - 它通过检查请求参数、WebSocket 路径 (`wsPath`) 和用户 ID (`userId`) 来决定是否允许某个 WebSocket 连接。 
 * 2. **关键字段**： 
 * - `adminId`：定义了一个管理员用户的唯一标识符，用于后续权限验证。 
 * 3. **核心逻辑**： 
 * - 方法 `doSelfDefinedCheck` 是重写的抽象方法，用于执行具体的检查逻辑。 
 * - 如果 `wsPath` 不是 `/ws`，直接返回 `true`，表示允许连接。 
 * - 如果 `wsPath` 是 `/ws`，则进一步检查 `userId` 是否与 `adminId` 相等。只有当 `userId` 等于 `adminId` 时，才允许连接。 
 * 4. **依赖的工具类**： 
 * - 使用了 `org.apache.commons.lang3.StringUtils` 提供的字符串比较方法（如 `equals`），确保字符串比较的安全性和正确性。 
 * 5. **适用场景**： 
 * - 该类主要用于限制 WebSocket 连接的访问权限，仅允许指定路径 (`/ws`) 的连接，并且只有特定的管理员用户可以建立连接。 
 * 总结：这是一个简单的 WebSocket 权限控制类，通过路径和用户 ID 的双重校验来限制连接的可用性，适用于需要严格权限管理的 WebSocket 场景。 
**/
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

