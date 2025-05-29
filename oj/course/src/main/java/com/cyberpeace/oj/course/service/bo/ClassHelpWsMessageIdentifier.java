package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 类帮助 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassHelpWsMessageIdentifier` 的类，该类继承自 `WsMessageIdentifier`。以下是对这段代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入语句**： 
 * - `com.cyberpeace.common.model.ws.WsMessageIdentifier`：引入了 `WsMessageIdentifier` 类。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解用于自动生成 getter 和 setter 方法。 
 * ### 类结构 
 * - **类名**：`ClassHelpWsMessageIdentifier` 
 * - **继承关系**：继承自 `WsMessageIdentifier` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * ### 成员变量 
 * - **classroomId**：类型为 `String`，表示课堂 ID。 
 * - 注释说明了该字段的作用。 
 * ### 作者和版本信息 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024/11/08 
 * ### 总结 
 * - 这个类的主要目的是扩展 `WsMessageIdentifier` 类，并添加一个额外的属性 `classroomId`。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 这个类可能用于处理与课堂相关的 WebSocket 消息标识符。 
 * 通过这段代码，我们可以看到它是一个简单的数据传输对象（DTO），用于在系统中传递课堂相关的消息标识符。 
**/
@Getter
@Setter
public class ClassHelpWsMessageIdentifier extends WsMessageIdentifier {

    /**
     * 课堂id
     *
     * @see String
     */
    private String classroomId;

}

