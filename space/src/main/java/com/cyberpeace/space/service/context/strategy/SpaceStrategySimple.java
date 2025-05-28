/**
 * 这段代码定义了一个名为 `SpaceStrategySimple` 的 Java 类，属于 `com.cyberpeace.space.service.context.strategy` 包。以下是简要分析： 
 * 1. **类的功能**： 
 * - 这是一个简单的 Java Bean 类，用于表示“空间策略”的基本属性。 
 * - 它主要用于存储与空间相关的策略信息。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，减少了冗余代码。 
 * 3. **字段描述**： 
 * - `resourceId`: 资源 ID，用于唯一标识资源。 
 * - `name`: 名称，可能描述策略的名称或标题。 
 * - `content`: 内容，存储策略的具体内容或描述。 
 * - `route`: 路线，可能指策略的应用路径或流程。 
 * - `builtin`: 布尔值，指示该策略是否为内置策略。 
 * 4. **用途**： 
 * - 此类可能是某个更复杂系统中的基础组件，用于存储和传递空间策略的相关信息。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装与空间策略相关的基础信息。 
**/
package com.cyberpeace.space.service.context.strategy;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间策略
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/11
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceStrategySimple {

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 内容
     *
     * @see String
     */
    private String content;

    /**
     * 路线
     *
     * @see String
     */
    private String route;

    /**
     * 内置
     *
     * @see Boolean
     */
    private Boolean builtin;
}
