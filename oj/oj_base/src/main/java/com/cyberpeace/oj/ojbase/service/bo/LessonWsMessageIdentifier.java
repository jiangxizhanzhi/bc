/**
 * 这段代码定义了一个名为 `LessonWsMessageIdentifier` 的 Java 类，该类继承自 `WsMessageIdentifier`。以下是详细的分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.ojbase.service.bo; 
 * ``` 
 * 这个包声明表示这个类位于 `com.cyberpeace.oj.ojbase.service.bo` 包中。 
 * ### 导入语句 
 * ```java 
 * import com.cyberpeace.common.model.ws.WsMessageIdentifier; 
 * import lombok.AllArgsConstructor; 
 * import lombok.Builder; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import lombok.NoArgsConstructor; 
 * ``` 
 * 这些导入语句引入了必要的外部类和 Lombok 注解： 
 * - `WsMessageIdentifier` 是一个基础类。 
 * - `@AllArgsConstructor`, `@NoArgsConstructor`, `@Getter`, `@Setter`, `@Builder` 是 Lombok 提供的注解，用于简化代码。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * @Builder 
 * @NoArgsConstructor 
 * @AllArgsConstructor 
 * public class LessonWsMessageIdentifier extends WsMessageIdentifier { 
 * ``` 
 * - `@Getter` 和 `@Setter` 注解为类的所有字段生成 getter 和 setter 方法。 
 * - `@Builder` 注解允许使用 Builder 模式来创建对象实例。 
 * - `@NoArgsConstructor` 和 `@AllArgsConstructor` 注解分别生成无参构造函数和全参构造函数。 
 * - 这个类继承自 `WsMessageIdentifier` 类。 
 * ### 字段定义 
 * ```java 
 * /** 
 * * 课id 
 * * 
 * * @see String 
 * *
 * private String lessonId; 
 * /** 
 * * 共享 
 * * 
 * * @see Boolean 
 * *
 * private Boolean shared; 
 * /** 
 * * 从 Web 
 * * 
 * * @see Boolean 
 * *
 * private Boolean fromWeb; 
 * ``` 
 * 这个类包含三个私有字段： 
 * - `lessonId`: 表示课程 ID。 
 * - `shared`: 表示是否共享。 
 * - `fromWeb`: 表示是否从 Web 获取。 
 * ### 总结 
 * - **目的**：`LessonWsMessageIdentifier` 类是 `WsMessageIdentifier` 类的一个扩展，用于表示与课程相关的 WebSocket 标识符。 
 * - **功能**：该类包含了课程 ID、是否共享以及是否从 Web 获取等信息。 
 * - **特性**：通过 Lombok 注解简化了代码，如自动生成 getter、setter、构造函数和 builder 方法。 
 * 这段代码主要用于处理与课程相关的 WebSocket 通信，并且通过 Lombok 注解减少了样板代码。 
**/
package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 第 WS 课文标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 共享
	 *
	 * @see Boolean
	 */
	private Boolean shared;

	/**
	 * 从 Web
	 *
	 * @see Boolean
	 */
	private Boolean fromWeb;

}
