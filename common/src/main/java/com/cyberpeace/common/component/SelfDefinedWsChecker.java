package com.cyberpeace.common.component;

import java.util.Map;

/**
 * 自定义ws连接校验器。
 * 红蓝对抗等业务模式中，需要根据用户的角色、判断是否允许用户建立连接。
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个抽象类 `SelfDefinedWsChecker`，用于自定义 WebSocket 连接的校验逻辑。以下是简要分析： 
 * ### 功能描述： 
 * - **用途**：该类用于在红蓝对抗等业务场景中，根据用户角色或其他条件判断是否允许用户建立 WebSocket 连接。 
 * - **扩展性**：通过抽象方法 `doSelfDefinedCheck` 提供了灵活的校验逻辑扩展能力。 
 * ### 关键点解析： 
 * 1. **抽象类**：`SelfDefinedWsChecker` 是一个抽象类，意味着不能直接实例化，必须由子类实现其抽象方法。 
 * 2. **抽象方法**：`doSelfDefinedCheck` 方法用于执行具体的校验逻辑，接收三个参数： 
 * - `requestParams`：WebSocket 连接时传递的业务参数（以 `Map` 形式存储）。 
 * - `wsPath`：请求的 WebSocket 连接地址。 
 * - `userId`：请求连接的用户 ID。 
 * - 返回值为布尔类型，`true` 表示校验通过，`false` 表示拒绝连接。 
 * 3. **注释清晰**：代码注释详细说明了类的用途、参数含义及返回值意义，便于理解和使用。 
 * ### 总结： 
 * 该代码提供了一个基础框架，用于实现复杂的 WebSocket 权限校验逻辑。通过继承此抽象类并实现 `doSelfDefinedCheck` 方法，开发者可以灵活地定制校验规则，适用于多样的业务需求。 
**/
public abstract class SelfDefinedWsChecker {

	/**
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

