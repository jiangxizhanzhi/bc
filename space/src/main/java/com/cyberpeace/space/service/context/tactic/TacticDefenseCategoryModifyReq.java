package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法防御类别修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see TacticDefenseCategoryAddReq
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `TacticDefenseCategoryModifyReq` 的类，它是 `TacticDefenseCategoryAddReq` 类的子类。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 该类用于表示战法防御类别修改请求的请求体。 
 * - 它继承自 `TacticDefenseCategoryAddReq`，表明它可能在某些功能上扩展或重用了父类的属性和方法。 
 * 2. **字段**： 
 * - `resourceId`：这是一个字符串类型的字段，用于存储资源 ID。注释表明了它的数据类型为 `String`。 
 * 3. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了该类的 getter 和 setter 方法，减少了手动编写这些方法的工作量。 
 * 4. **文档注释**： 
 * - 类和字段都有详细的 Javadoc 注释，说明了类的用途、字段的意义以及相关的引用类。 
 * 总结： 
 * 该类是一个简单的 POJO（Plain Old Java Object），主要用于封装战法防御类别修改请求的相关信息，特别是新增了一个 `resourceId` 字段来标识资源。 
**/
@Getter
@Setter
public class TacticDefenseCategoryModifyReq extends TacticDefenseCategoryAddReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}

