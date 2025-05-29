package com.cyberpeace.common.model.ws;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 帮助聊天消息
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `HelpChatMessage` 的 Java 类，它是 `ChatMessage` 类的子类。以下是简要分析： 
 * 1. **类的功能**： 
 * - `HelpChatMessage` 是一个帮助聊天消息的模型类，用于存储与帮助相关的聊天信息。 
 * 2. **继承关系**： 
 * - 它继承自 `ChatMessage` 类，表明它扩展了 `ChatMessage` 的功能或属性。 
 * 3. **字段**： 
 * - 添加了一个私有字段 `role`，类型为 `String`，表示岗位信息。 
 * 4. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * 5. **作者与日期**： 
 * - 代码由 SN 创建，创建日期为 2024 年 11 月 8 日。 
 * 总结：该类主要用于存储帮助相关聊天消息的额外信息（如岗位），并通过继承和注解增强了代码的简洁性和可维护性。 
**/
@Getter
@Setter
public class HelpChatMessage extends ChatMessage {

	/**
	 * 岗位
	 */
	private String role;

}

