package com.cyberpeace.common.model.ws;

import lombok.Getter;
import lombok.Setter;

/**
 * 聊天内容
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ChatContent` 的 Java 类，用于表示聊天内容。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装聊天信息，包含两个主要字段：`to` 和 `mine`。 
 * - `to` 字段指向接收方的信息（类型为 `ChatToContent`）。 
 * - `mine` 字段指向发送方的信息（类型为 `ChatMineContent`）。 
 * 2. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * 3. **方法**： 
 * - `isGroup()`：判断聊天是否是群聊，通过检查 `to.getType()` 是否等于 `"group"`。 
 * - `getGroupId()`：获取群组的 ID，直接从 `to.getId()` 返回。 
 * - `getReceiverId()`：获取接收者的 ID，同样从 `to.getId()` 返回。 
 * - `getMineId()`：获取发送方的 ID，从 `mine.getId()` 返回。 
 * 4. **依赖**： 
 * - 该类依赖于两个内部类或外部类：`ChatToContent` 和 `ChatMineContent`，但代码中未提供这些类的具体实现。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装聊天信息，并提供了方便的方法来获取特定的聊天属性。 
**/
@Getter
@Setter
public class ChatContent {

	private ChatToContent to;

	private ChatMineContent mine;

	/**
	 * is 组
	 *
	 * @return boolean
	 */
	public boolean isGroup() {
		return to.getType().equals("group");
	}

	/**
	 * 获取群id
	 *
	 * @return {@code String }
	 */
	public String getGroupId() {
		return to.getId();
	}

	/**
	 * 获取接收器id
	 *
	 * @return {@code String }
	 */
	public String getReceiverId() {
		return to.getId();
	}

	/**
	 * 获取矿山id
	 *
	 * @return {@code String }
	 */
	public String getMineId() {
		return mine.getId();
	}

}

