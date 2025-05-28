/**
 * 这段代码定义了一个名为 `ClassroomWsMessageIdentifier` 的类，该类继承自 `WsMessageIdentifier`。以下是对这段代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.ojbase.service.bo` 
 * - **类名**: `ClassroomWsMessageIdentifier` 
 * - **继承**: 继承自 `WsMessageIdentifier` 
 * ### 注解 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * ### 类成员变量 
 * - `private String classroomId;`: 这个字段表示课堂的唯一标识符（ID）。 
 * ### Javadoc 
 * - 类的 Javadoc 描述了这个类的作用：它是用于课堂的 WebSocket 消息标识符。 
 * - 字段 `classroomId` 的 Javadoc 说明了该字段的作用，并引用了其类型 `String`。 
 * ### 总结 
 * - `ClassroomWsMessageIdentifier` 类是 `WsMessageIdentifier` 类的一个扩展，用于在 WebSocket 通信中标识与某个特定课堂相关的消息。 
 * - 该类包含一个 `classroomId` 字段，用于存储课堂的唯一标识符。 
 * - 使用 Lombok 的注解简化了 getter 和 setter 方法的生成。 
 * 这段代码主要用于在 WebSocket 消息系统中标识与特定课堂相关的信息。 
**/
package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 课堂 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassroomWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

}
