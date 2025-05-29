package com.cyberpeace.common.model.ws;

import java.io.Serializable;

import lombok.*;

/**
 * Web 套接字消息上下文
 *
 * @author yinqingrong@date 2024/11/08
 */
/**
 * 该代码定义了一个通用的 `WebSocketMessageContext` 类，用于封装 WebSocket 消息的上下文信息。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类表示 WebSocket 消息的上下文，包含消息标识符 (`messageIdentifier`) 和数据 (`data`)。 
 * 2. **泛型设计**： 
 * - 使用了两个泛型参数： 
 * - `I`：表示消息标识符的类型，并限制为实现 `WsMessageIdentifier` 接口的子类型。 
 * - `M`：表示消息数据的类型。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 的注解简化代码： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - `@Builder`：支持构建器模式，方便实例化对象。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成全参构造函数和无参构造函数。 
 * 4. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，适合在网络传输中使用。 
 * 5. **适用场景**： 
 * - 适用于需要灵活定义消息标识符和数据类型的 WebSocket 应用场景。 
 * 总结：这是一个通用且灵活的 WebSocket 消息上下文类，便于在 WebSocket 通信中传递结构化的消息。 
**/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessageContext<I extends WsMessageIdentifier, M> implements Serializable {

	/**
	 * 消息标识符
	 */
	private I messageIdentifier;

	/**
	 * 数据
	 */
	private M data;

}

