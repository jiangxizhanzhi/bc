/**
 * 这段代码定义了一个名为 `DefenseListBo` 的类，它是 `AttackListBo` 类的子类。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - `DefenseListBo` 是一个业务对象（BO），用于表示防御策略列表的相关信息。 
 * - 它继承自 `AttackListBo`，表明它可能共享一些与攻击相关的属性或行为。 
 * 2. **注释和文档**： 
 * - 类上有详细的 Javadoc 注释，说明了其用途、作者、版本、日期以及相关联的其他类（如 `AttackListBo`）。 
 * - 这种注释有助于开发者快速理解类的设计意图和上下文。 
 * 3. **属性**： 
 * - 虽然当前类的属性被注释掉了，但从注释中可以看出，它原本可能包含以下属性： 
 * - `resourceId`, `name`, `tacticId`, `categoryId`, `category`, `description`, `scene`, 和 `tool`。 
 * - 这些属性可能是用来描述防御策略的关键信息。 
 * 4. **Lombok 注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法，简化了代码量并提高了可读性。 
 * 5. **继承关系**： 
 * - 通过继承 `AttackListBo`，`DefenseListBo` 可能复用了 `AttackListBo` 中的一些通用逻辑或数据结构，减少了重复代码。 
 * 总结来说，这个类是一个专门用于存储防御策略相关信息的业务对象，具有良好的注释和使用了 Lombok 提高开发效率。 
**/
package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 防御列表Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see AttackListBo
 * @since 1.0.0
 */
@Getter
@Setter
public class DefenseListBo extends AttackListBo {
//    private String resourceId;
//
//    private String name;
//
//    private String tacticId;
//
//    private String categoryId;
//
//    private String category;
//
//    private String description;
//
//    private String scene;
//
//    private List<String> tool;
}
