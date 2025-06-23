package com.cyberpeace.common.model.ws;

import com.google.common.collect.Maps;
import lombok.*;

import java.util.Map;

/**
 * websocket连接需要的上下文
 *
 * @author yinqingrong@date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `WebSocketConnectionContext` 的类，用于存储 WebSocket 连接的上下文信息。以下是对其的简要分析： 
 * 1. **继承与注解**： 
 * - 该类继承自 `WsMessageIdentifier`，表明它可能包含与消息标识相关的功能。 
 * - 使用了 Lombok 提供的注解 (`@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@Builder`) 来简化代码，自动生成构造函数、getter 和 setter 方法以及构建器模式。 
 * 2. **成员变量**： 
 * - `originParams`：一个 `Map<String, String>` 类型的字段，默认使用 `Maps.newTreeMap()` 初始化，表示建立连接时的原始请求参数。使用 `TreeMap` 可以保证键值对按照自然顺序排序。 
 * 3. **方法**： 
 * - 重写的 `getIdentifierId` 方法通过遍历 `originParams` 中的所有键值对，并将它们的值拼接成一个字符串返回，每个值之间用 `&` 分隔。这个方法可能是为了生成一个唯一的标识符用于区分不同的 WebSocket 连接。 
 * 总体而言，这个类设计用于封装 WebSocket 连接的相关上下文信息，并提供了方便的方法来处理这些信息。 
**/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketConnectionContext extends WsMessageIdentifier {

	/**
	 * 建立连接时的原始请求信息
	 */
	private Map<String, String> originParams = Maps.newTreeMap();

	/**
	 * 获取业务id
	 * @return
	 */
	@Override
	public String getIdentifierId() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> item : originParams.entrySet()) {
			stringBuilder.append(item.getValue()).append('&');
		}

		return stringBuilder.toString();
	}
}

