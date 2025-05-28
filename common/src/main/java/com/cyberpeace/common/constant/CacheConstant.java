/**
 * 这段代码定义了一个名为 `CacheConstant` 的 Java 接口，用于存储与缓存相关的常量。以下是简要分析： 
 * 1. **功能描述**： 
 * - 该接口主要用于集中管理应用程序中的缓存相关常量。 
 * - 常量以静态字符串的形式定义，便于在其他地方引用。 
 * 2. **具体常量**： 
 * - `REMOTE_USER`：表示 MP 用户缓存的键值，用于远程用户信息的缓存。 
 * - `CR_TOOL_CATEGORY`：表示靶场工具类别的缓存键值。 
 * - `REMOTE_TOPO`：表示远程拓扑的缓存键值。 
 * 3. **设计特点**： 
 * - 使用接口来定义常量是一种常见的 Java 编码规范，避免了常量被实例化或修改的可能性。 
 * - 所有常量均为 `String` 类型，方便在缓存操作中使用。 
 * 4. **适用场景**： 
 * - 适用于需要统一管理缓存键值的场景，有助于代码的可维护性和一致性。 
 * 总结：这是一个简单的缓存常量管理接口，定义了三个与缓存相关的字符串常量，适合在需要统一缓存键值的项目中使用。 
**/
package com.cyberpeace.common.constant;

/**
 * 缓存常量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface CacheConstant {

	/**
	 * mp用户缓存
	 *
	 * @see String
	 */
	String REMOTE_USER = "remote_user";

	/**
	 * 靶场工具类别
	 *
	 * @see String
	 */
	String CR_TOOL_CATEGORY = "cr_tool_category";

	/**
	 * 远程拓扑
	 *
	 * @see String
	 */
	String REMOTE_TOPO = "remote_topo";
}
