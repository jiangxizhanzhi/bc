package com.cyberpeace.common.model.user;

/**
 * 用户信息上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个用于管理用户信息上下文的工具类 `UserInfoContext`，以下是简要分析： 
 * ### 功能概述： 
 * - 提供线程隔离的用户信息存储机制。 
 * - 使用 `ThreadLocal` 来确保每个线程拥有独立的用户信息实例。 
 * ### 关键点分析： 
 * 1. **线程安全**： 
 * - `ThreadLocal` 的使用保证了不同线程之间的数据隔离，避免多线程并发访问时的数据冲突。 
 * 2. **核心方法**： 
 * - `getUserContext()`：获取当前线程的用户信息。 
 * - `setUserContext(UserInfo userInfo)`：设置当前线程的用户信息。 
 * - `removeUserContext()`：移除当前线程的用户信息（通常在请求结束时调用以清理资源）。 
 * 3. **设计模式**： 
 * - 单例模式：通过私有构造函数限制外部实例化。 
 * 4. **潜在问题**： 
 * - 如果忘记调用 `removeUserContext()`，可能会导致内存泄漏或数据污染。 
 * - 缺乏输入参数校验，例如在 `setUserContext()` 方法中未检查传入的 `userInfo` 是否为 `null`。 
 * ### 总结： 
 * 该类是一个典型的线程本地存储实现，适用于需要在线程范围内传递用户信息的场景。但需要注意合理管理生命周期，及时清理不再使用的上下文。 
**/
public class UserInfoContext {

	/**
	 * 用户上下文
	 */
	private static final ThreadLocal<UserInfo> userContext = new ThreadLocal();

	/**
	 * 用户信息上下文
	 */
	private UserInfoContext() {}

	/**
	 * 获取用户上下文
	 *
	 * @return {@code UserInfo }
	 */
	public static UserInfo getUserContext() {
		return userContext.get();
	}

	/**
	 * 设置用户上下文
	 *
	 * @param userInfo 用户信息
	 */
	public static void setUserContext(UserInfo userInfo) {
		userContext.set(userInfo);
	}

	/**
	 * 删除用户上下文
	 */
	public static void removeUserContext() {
		userContext.remove();
	}
}

