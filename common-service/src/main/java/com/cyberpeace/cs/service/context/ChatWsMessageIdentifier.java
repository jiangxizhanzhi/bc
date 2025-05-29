package com.cyberpeace.cs.service.context;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Chat WS 消息标识符
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `ChatWsMessageIdentifier` 的类，它是 `WsMessageIdentifier` 的子类。这个类主要用于标识聊天相关的 WebSocket 消息。 
 * ### 主要特点： 
 * 1. **继承关系**：`ChatWsMessageIdentifier` 继承自 `WsMessageIdentifier`，表明它扩展了 `WsMessageIdentifier` 的功能。 
 * 2. **字段说明**： 
 * - `businessId`: 用于表示特定业务场景下的唯一标识符，例如课堂或红蓝对抗房间的编号。 
 * - `businessType`: 表示聊天室的类型，可能是一个枚举值或其他分类标识。 
 * 3. **注解使用**：使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码量。 
 * 4. **文档注释**：类和字段都有详细的 Javadoc 注释，清晰地描述了其用途和意义。 
 * ### 总结： 
 * 这是一个简单的 POJO（Plain Old Java Object）类，用于封装与聊天相关的 WebSocket 消息标识信息，便于在系统中传递和处理。 
**/
@Getter
@Setter
public class ChatWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 聊天室业务编号 课堂=课堂表编号 红蓝=红蓝对抗房间编号
	 */
	private String businessId;

	/**
	 * 聊天室类型
	 */
	private Integer businessType;

}

