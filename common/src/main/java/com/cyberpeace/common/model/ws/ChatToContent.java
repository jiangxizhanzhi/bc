/**
 * 这段代码定义了一个名为 `ChatToContent` 的 Java 类，用于表示与聊天相关的数据模型。以下是对其的简要分析： 
 * ### 功能描述 
 * 该类主要用于封装聊天消息或内容的相关信息，包括用户、分组以及一些通用属性。通过注释可以看出，它旨在实现“聊天到内容”的转换。 
 * ### 关键点总结 
 * 1. **类结构**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 类中包含多个私有字段，每个字段都有对应的注释说明其用途。 
 * 2. **字段解析**： 
 * - `role`: 表示岗位。 
 * - `id`: 消息或对象的唯一标识符。 
 * - `avatar`: 用户头像。 
 * - `name`: 用户名称。 
 * - `type`: 类型字段。 
 * - `time`: 服务器时间。 
 * - `groupname`: 分组名称（仅分组相关）。 
 * - `members`: 成员列表。 
 * - `username`: 用户名（仅好友相关）。 
 * - `sign`: 标志字段。 
 * - `status`: 状态字段。 
 * - `uuid`: 唯一的 UUID。 
 * 3. **设计意图**： 
 * - 该类是一个数据传输对象 (DTO)，用于在不同模块之间传递聊天相关的数据。 
 * - 它支持多种场景（如分组、好友等），但同时也保持了灵活性，可以通过字段的注释区分哪些字段适用于特定场景。 
 * 4. **扩展性**： 
 * - 当前版本已经包含了较多的通用字段，未来如果需要添加更多属性，可以直接在类中新增字段并补充注释。 
 * ### 总结 
 * `ChatToContent` 是一个简洁且功能明确的数据模型类，适合用作聊天系统的中间层数据载体。通过使用 Lombok 减少了样板代码量，同时良好的注释使得维护和扩展变得容易。 
**/
package com.cyberpeace.common.model.ws;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Chat to Content （聊天到内容）
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ChatToContent {

	/**
	 * 岗位
	 */// 公共字段
	private String role;

	/**
	 * id
	 */
	private String id;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 时间
	 */// 后面增加的服务器时间
	private String time;

	/**
	 * 组名
	 */// 分组独有
	private String groupname;

	/**
	 * 成员
	 */
	private List<String> members;

	/**
	 * 用户名
	 */// 好友独有
	private String username;

	/**
	 * 标志
	 */
	private String sign;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * uuid
	 */
	private String uuid;
}
