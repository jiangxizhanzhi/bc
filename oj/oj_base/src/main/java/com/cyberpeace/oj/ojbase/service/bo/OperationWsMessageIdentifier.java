/**
 * 这段代码定义了一个名为 `OperationWsMessageIdentifier` 的类，该类继承自 `WsMessageIdentifier`。以下是对这段代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.ojbase.service.bo` 
 * - **导入语句**：导入了 `WsMessageIdentifier` 类，并使用了 Lombok 库的注解。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：为所有字段生成 getter 和 setter 方法。 
 * - `@AllArgsConstructor`：生成包含所有字段的构造函数。 
 * - `@NoArgsConstructor`：生成不带参数的构造函数。 
 * - `@Builder`：生成一个构建器模式的静态方法，用于方便地创建对象实例。 
 * ### 类结构 
 * - **继承关系**：`OperationWsMessageIdentifier` 继承自 `WsMessageIdentifier`。 
 * ### 字段 
 * - **practiceId**：表示实践 ID，类型为 `String`。 
 * - **fromWeb**：表示消息是否来自 Web 端，类型为 `Boolean`。 
 * - **shared**：表示某种共享状态，类型为 `Integer`。 
 * ### 文档 
 * - 类和字段都有 Javadoc 注释，描述了类和字段的作用。 
 * ### 总结 
 * 这个类主要用于封装与操作相关的 WebSocket 消息标识符信息。通过 Lombok 注解简化了代码，减少了样板代码的编写。 
**/
package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 实操WS消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 实践id
	 *
	 * @see String
	 */
	private String practiceId;

	/**
	 * 从 Web
	 *
	 * @see Boolean
	 */
	private Boolean fromWeb;

	/**
	 * 共享
	 *
	 * @see Integer
	 */
	private Integer shared;

}
