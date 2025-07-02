package com.cyberpeace.common.model.ws;

import lombok.*;

/**
 * 聊天消息
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `ChatMessage` 的 Java 类，用于表示聊天消息的模型。以下是代码的主要特点和功能分析： 
 * 1. **注解与特性**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - `@Builder`：提供链式构建器模式，方便对象创建。 
 * - `@NoArgsConstructor` 和 `@AllArgsConstructor`：分别生成无参构造函数和全参构造函数。 
 * 2. **字段说明**： 
 * - `username`：表示发送者的用户名。 
 * - `avatar`：用户的头像信息。 
 * - `id`：接收者看到的聊天框标识（群聊时为群ID，私聊时为发送者ID）。 
 * - `type`：消息类型。 
 * - `content`：消息内容。 
 * - `cid`：消息的唯一标识符，默认值为 0。 
 * - `mine`：布尔值，表示消息是否来自当前用户，默认值为 `false`。 
 * - `fromid`：消息来源的用户ID。 
 * - `time`：消息显示的时间格式。 
 * - `timestamp`：消息的时间戳，通过 `setTimestamp()` 方法自动设置为当前系统时间。 
 * - `uuid`：消息的唯一UUID标识。 
 * 3. **方法**： 
 * - `setTimestamp()`：设置消息的时间戳为当前系统时间。 
 * 4. **用途**： 
 * - 该类主要用于封装聊天消息的数据结构，支持多种场景（如群聊和私聊），便于在 WebSocket 或其他通信协议中传递消息数据。 
 * 总结：这是一个简洁且功能完善的聊天消息模型类，使用 Lombok 注解减少了冗余代码，同时提供了灵活的对象创建方式和必要的字段管理。 
**/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * 接收者看到的聊天框(群聊为群id，私聊为发送者id)
	 */
	private String id;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * CID
	 */
	private final Integer cid = 0;

	/**
	 * 矿山
	 */
	private final Boolean mine = Boolean.FALSE;

	/**
	 * fromid
	 */
	private String fromid;

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 时间戳
	 */
	private long timestamp;

//	/**
//	 * 设置时间戳
//	 */
//	public void setTimestamp() {
//		timestamp = System.currentTimeMillis();
//	}

	/**
	 * uuid
	 */
	private String uuid;
}

