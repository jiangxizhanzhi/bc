/**
 * 这段代码定义了一个名为 `DemoWsMessage` 的 Java 类，用于模拟通过 WebSocket 发送的消息。以下是简要分析： 
 * 1. **功能描述**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输。 
 * - 它是一个用于 WebSocket 消息的模型类，包含时间戳、代号和消息内容。 
 * 2. **主要特性**： 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - 使用了 Lombok 的 `@Builder` 注解，提供了一种方便的方式来构建对象实例。 
 * 3. **字段说明**： 
 * - `timestamp`：记录消息的时间戳，类型为 `Date`。 
 * - `code`：表示消息的代号，类型为 `Integer`。 
 * - `message`：存储消息的具体内容，类型为 `String`。 
 * 4. **注释**： 
 * - 类和字段都有详细的注释，解释了每个字段的用途和相关引用类型。 
 * 总体而言，这是一个简洁且功能明确的类，适用于 WebSocket 消息的封装和传递。 
**/
package com.cyberpeace.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 模拟实际需要发送的websocket消息
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Builder
public class DemoWsMessage implements Serializable {

	/**
	 * 时间戳
	 *
	 * @see Date
	 */
	private Date timestamp;

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	private Integer code;

	/**
	 * 消息
	 *
	 * @see String
	 */
	private String message;
}
