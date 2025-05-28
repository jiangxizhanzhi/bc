/**
 * 这段代码定义了一个名为 `GroupWsMessageIdentifier` 的类，该类继承自 `WsMessageIdentifier` 类，并添加了一个新的字段 `groupId`。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入语句**： 
 * - `com.cyberpeace.common.model.ws.WsMessageIdentifier`：表示这个类将继承自 `WsMessageIdentifier` 类。 
 * - `lombok.Getter` 和 `lombok.Setter`：使用 Lombok 注解自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * - **引用**：`@see WsMessageIdentifier` 表示这个类与 `WsMessageIdentifier` 类有某种关联或扩展关系。 
 * ### 类属性 
 * - **groupId**：一个字符串类型的变量，用于存储群组的 ID。 
 * ### Getter 和 Setter 
 * - 使用 `@Getter` 和 `@Setter` 注解自动生成了 `groupId` 字段的 getter 和 setter 方法。 
 * ### 总结 
 * - `GroupWsMessageIdentifier` 类继承自 `WsMessageIdentifier` 类，并新增了一个字段 `groupId`，用于表示群组的唯一标识符。 
 * - 通过 Lombok 注解简化了 getter 和 setter 方法的生成。 
 * - 这个类可能是用于处理与群组相关的 WebSocket 消息标识符。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Getter;
import lombok.Setter;

/**
 * 组 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
public class GroupWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 群id
	 *
	 * @see String
	 */
	private String groupId;

}
