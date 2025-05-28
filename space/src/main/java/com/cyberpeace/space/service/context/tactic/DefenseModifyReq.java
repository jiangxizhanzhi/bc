/**
 * 这段代码定义了一个名为 `DefenseModifyReq` 的 Java 类，它是 `DefenseAddReq` 的子类。以下是对代码的简要分析： 
 * 1. **类的功能**： 
 * - `DefenseModifyReq` 是一个用于处理防御修改请求的类。 
 * - 它继承自 `DefenseAddReq`，表明它可能在某些方面扩展或重用了父类的功能。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的getter和setter方法，减少了样板代码。 
 * 3. **新增字段**： 
 * - 添加了一个新的私有字段 `resourceId`，类型为 `String`，用于标识资源ID。 
 * 4. **文档说明**： 
 * - 类和字段都有 Javadoc 注释，提供了清晰的描述，便于开发者理解其用途。 
 * 总结：该类通过继承和新增字段的方式，扩展了 `DefenseAddReq` 的功能，主要用于处理包含资源ID的防御修改请求。 
**/
package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 防御修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see DefenseAddReq
 * @since 1.0.0
 */
@Getter
@Setter
public class DefenseModifyReq extends DefenseAddReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}
