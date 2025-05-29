package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂场景技巧WS消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomSceneTipsWsMessageIdentifier` 的类，该类继承自 `WsMessageIdentifier`。以下是对其简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `ClassroomSceneTipsWsMessageIdentifier` 
 * - **继承**: 继承自 `WsMessageIdentifier` 
 * ### 类注释 
 * - **作者**: SN 
 * - **版本**: 1.0.0 
 * - **日期**: 2024年11月8日 
 * - **继承的类**: `WsMessageIdentifier` 
 * ### 类属性 
 * - **roomId**: 
 * - **类型**: `String` 
 * - **描述**: 表示房间ID 
 * ### Getter 和 Setter 
 * - 使用 Lombok 注解 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * ### 总结 
 * 这个类用于表示课堂场景技巧中的 WebSocket 消息标识符，并且包含一个房间ID属性。通过继承 `WsMessageIdentifier` 类，它可能用于在特定的教室场景中发送和接收消息。 
**/
@Getter
@Setter
public class ClassroomSceneTipsWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

}

