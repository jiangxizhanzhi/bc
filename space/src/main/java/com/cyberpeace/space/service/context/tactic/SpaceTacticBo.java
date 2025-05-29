package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see AttackListBo
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTacticBo` 的 Java 类，它继承自 `AttackListBo`。以下是代码的主要特点和功能分析： 
 * 1. **类的功能**： 
 * - `SpaceTacticBo` 是一个业务对象（BO），用于表示与空间战法相关的数据模型。 
 * - 它扩展了 `AttackListBo` 类，表明它可能包含与攻击列表相关的一些通用属性或行为。 
 * 2. **注解和字段**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了 `type` 字段的 getter 和 setter 方法。 
 * - `type` 字段是一个整数，通过 Swagger 的 `@ApiModelProperty` 注解描述其含义：值为 `1` 表示攻击，值为 `2` 表示防御。 
 * 3. **设计意图**： 
 * - 该类的设计目的是封装空间战法的相关信息，特别是区分是攻击还是防御的类型。 
 * - 通过继承 `AttackListBo`，可以复用其已有功能，并专注于扩展新的特性。 
 * 4. **元信息**： 
 * - 类上有详细的 Javadoc 注释，说明了类的用途、作者、版本以及与其他类的关系。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于在空间战法场景中传递类型信息（攻击或防御）。 
**/
@Getter
@Setter
public class SpaceTacticBo extends AttackListBo {
    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty(value = "类型, 1攻击，2防御")
    private Integer type;
}

