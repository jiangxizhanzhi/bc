package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课程练习 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeWsMessageIdentifier` 的 Java 类，该类继承自 `WsMessageIdentifier`。这个类主要用于表示与课程练习相关的 WebSocket 消息标识符。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.service.bo` 
 * - **导入的类**： 
 * - `com.cyberpeace.common.model.ws.WsMessageIdentifier` 
 * - Lombok 注解（`@AllArgsConstructor`, `@NoArgsConstructor`, `@Getter`, `@Setter`, `@Builder`） 
 * ### 类注释 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024/11/08 
 * - **相关类**：`WsMessageIdentifier` 
 * ### 类属性 
 * - **lessonId**：表示课程的 ID，类型为 `String` 
 * - **practiceId**：表示实践的 ID，类型为 `String` 
 * - **fromWeb**：表示消息是否来自 Web 端，类型为 `Boolean` 
 * - **shared**：表示是否共享，类型为 `Integer` 
 * ### Lombok 注解 
 * - **@Getter** 和 **@Setter**：自动生成 getter 和 setter 方法。 
 * - **@Builder**：允许使用构建器模式创建对象实例。 
 * - **@NoArgsConstructor** 和 **@AllArgsConstructor**：分别生成无参构造函数和全参构造函数。 
 * ### 总结 
 * - **用途**：`LessonPracticeWsMessageIdentifier` 类用于封装与课程练习相关的 WebSocket 消息标识符信息。 
 * - **继承关系**：继承自 `WsMessageIdentifier` 类，表明它是一个特定类型的 WebSocket 消息标识符。 
 * - **特性**：使用 Lombok 注解简化了代码，减少了样板代码。 
 * 这个类的设计简洁且功能明确，适用于需要处理与课程练习相关的 WebSocket 消息场景。 
**/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonPracticeWsMessageIdentifier extends WsMessageIdentifier {

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

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

