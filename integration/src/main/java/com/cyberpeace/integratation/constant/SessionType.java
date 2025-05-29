package com.cyberpeace.integratation.constant;

/**
 * 会话类型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SessionType` 的枚举类，用于表示不同的会话类型。以下是对该代码的简要分析： 
 * ### 枚举类 `SessionType` 
 * - **枚举值**： 
 * - `ALL`：表示所有类型的会话，包含监控路径和远程协助路径。 
 * - `ASSISTANCE`：表示仅获取远程协助路径的会话。 
 * - **字段**： 
 * - `type`：字符串类型，表示会话类型的标识符。 
 * - `desc`：字符串类型，表示会话类型的描述。 
 * - **构造函数**： 
 * - `SessionType(String type, String desc)`：接受两个参数，分别为 `type` 和 `desc`，并将其赋值给对应的字段。 
 * ### 总结 
 * 这个枚举类主要用于定义和管理不同的会话类型，每个枚举值都有一个唯一的标识符和描述。通过这种方式，可以方便地管理和使用这些会话类型。 
**/
public enum SessionType {

	/**
	 * 都
	 */
	ALL("all", "监控路径和与远程协助路径"),
	/**
	 * 援助
	 */
	ASSISTANCE("assistance", "仅获取远程协助路径");

	/**
	 * 类型
	 */
	public String type;

	/**
	 * 描述
	 */
	public String desc;

	SessionType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}
