package com.cyberpeace.model;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Builder;
import lombok.Data;

/**
 * ws消息标识的demo。
 * <p>
 * <p>
 * <p>
 * 声明的字段和请求ws连接时的字段一致！
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `DemoWsMessageIdentifier` 的类，它是 `WsMessageIdentifier` 类的子类，并使用了 Lombok 提供的注解来简化代码。以下是代码的详细分析： 
 * 1. **包名与导入**： 
 * - 包名为 `com.cyberpeace.model`，表明这个类属于该包下的模型类。 
 * - 导入了 `com.cyberpeace.common.model.ws.WsMessageIdentifier`，表示 `DemoWsMessageIdentifier` 继承自这个类。 
 * - 使用了 Lombok 的 `@Builder` 和 `@Data` 注解。 
 * 2. **类注释**： 
 * - 文档说明了这是一个用于演示的 WebSocket 消息标识类，声明的字段与请求 WebSocket 连接时使用的字段一致。 
 * - 强调了继承自 `WsMessageIdentifier` 类，并引用了其相关类。 
 * 3. **类定义**： 
 * - `@Builder`：为类生成一个建造者模式的构造方法。 
 * - `@Data`：自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - 类 `DemoWsMessageIdentifier` 继承自 `WsMessageIdentifier`，意味着它可能扩展或覆盖父类的功能。 
 * 4. **字段定义**： 
 * - `lessonId`：表示课时 ID，类型为 `String`。 
 * - `courseId`：表示课程 ID，类型为 `String`。 
 * - 字段都有 Javadoc 注释，明确描述了它们的用途。 
 * 总结：这是一个基于 Lombok 的简洁实现，用于表示 WebSocket 消息标识的示例类，继承自 `WsMessageIdentifier` 并添加了两个特定字段。 
**/
@Builder
@Data
public class DemoWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课时id
	 *
	 * @see String
	 */
	String lessonId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	String courseId;
}

