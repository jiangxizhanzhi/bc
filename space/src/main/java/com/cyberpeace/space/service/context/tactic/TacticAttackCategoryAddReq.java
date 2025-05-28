/**
 * 该代码定义了一个名为 `TacticAttackCategoryAddReq` 的 Java 类，位于包 `com.cyberpeace.space.service.context.tactic` 中。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，用于接收战法攻击类别新增操作的相关信息。 
 * - 注释表明其用途是支持“战法攻击类别”的新增功能。 
 * 2. **注解与属性**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法。 
 * - 类中仅包含一个字段： 
 * - `name`：表示名称，类型为 `String`。 
 * 3. **代码结构**： 
 * - 简洁明了，只包含必要的字段和注解。 
 * - 通过注释清晰地描述了类的用途及其字段的含义。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装新增战法攻击类别时需要的名称信息。 
**/
package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法攻击类别新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticAttackCategoryAddReq {
    /**
     * 名称
     *
     * @see String
     */
    private String name;
    }
