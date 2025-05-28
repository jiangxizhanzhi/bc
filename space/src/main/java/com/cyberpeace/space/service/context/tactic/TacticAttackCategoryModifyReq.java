/**
 * 该代码定义了一个名为 `TacticAttackCategoryModifyReq` 的 Java 类，继承自 `TacticAttackCategoryAddReq`。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 这是一个用于战法攻击类别修改请求的实体类。 
 * - 它扩展了 `TacticAttackCategoryAddReq`，可能在某些功能上重用了其属性或方法。 
 * 2. **关键字段**： 
 * - `resourceId`：表示资源 ID，类型为 `String`。注释说明了该字段的用途。 
 * 3. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法，减少了样板代码。 
 * 4. **设计意图**： 
 * - 通过继承 `TacticAttackCategoryAddReq`，可能是为了复用其已有逻辑，同时添加了新的字段（`resourceId`）以满足修改请求的需求。 
 * 5. **版本与引用**： 
 * - 注释中提到了作者、版本号（1.0.0）、日期（2025/05/28），以及相关类 `TacticAttackCategoryAddReq`，表明这是一个有明确上下文的业务类。 
 * 总结：该类是一个专门用于战法攻击类别修改的请求体类，通过继承增强了功能，并使用 Lombok 提高了代码简洁性。 
**/
package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法攻击类别修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see TacticAttackCategoryAddReq
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticAttackCategoryModifyReq extends TacticAttackCategoryAddReq {
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}
