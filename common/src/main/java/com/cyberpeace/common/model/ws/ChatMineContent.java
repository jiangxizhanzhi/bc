package com.cyberpeace.common.model.ws;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 聊天挖矿内容
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ChatMineContent` 的 Java 类，位于包 `com.cyberpeace.common.model.ws` 中。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类用于表示与聊天挖矿相关的数据模型。 
 * - 主要包含用户在聊天中涉及挖矿相关的信息。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码冗余。 
 * 3. **字段说明**： 
 * - `username`: 用户名。 
 * - `avatar`: 用户头像。 
 * - `id`: 唯一标识符。 
 * - `mine`: 表示是否涉及挖矿。 
 * - `content`: 聊天内容。 
 * - `time`: 消息发送的时间（后来添加）。 
 * - `uuid`: 唯一的 UUID 标识。 
 * 4. **设计目的**： 
 * - 该类封装了聊天挖矿所需的核心信息，便于在系统中传递和使用。 
 * 总结：这是一个简单的数据传输对象（DTO），用于存储和传输聊天挖矿相关的数据信息。 
**/
@Getter
@Setter
public class ChatMineContent {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * id
	 */
	private String id;

	/**
	 * 矿山
	 */
	private Boolean mine;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 时间
	 */// 后面增加的服务器时间
	private String time;

	/**
	 * uuid
	 */
	private String uuid;
}

