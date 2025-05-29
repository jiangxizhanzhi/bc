package com.cyberpeace.common.model.room;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 挡泥板岗位
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `DashboardRole` 的 Java 类，用于表示“挡泥板岗位”的相关数据模型。以下是代码的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.common.model.room` 包中。 
 * 2. **依赖引入**： 
 * - 使用了 Lombok 提供的注解 `@Getter` 和 `@Setter`，自动生成类的 getter 和 setter 方法，简化了代码书写。 
 * 3. **类功能**： 
 * - 类名为 `DashboardRole`，表明它与“挡泥板岗位”相关。 
 * - 类中有三个属性： 
 * - `roleType`：表示岗位类型。 
 * - `role`：表示具体的岗位名称。 
 * - `roleCode`：表示岗位的代号，数据类型为 `Integer`。 
 * 4. **注释**： 
 * - 类上附有 Javadoc 注释，说明该类的功能是“挡泥板岗位”。 
 * - 每个字段也有对应的 Javadoc 注释，描述其含义。 
 * 总结：这是一个简单的数据传输对象（DTO），用于存储和传递“挡泥板岗位”的相关信息。通过 Lombok 注解减少了样板代码，提高了开发效率。 
**/
@Getter
@Setter
public class DashboardRole {
    /**
     * 岗位类型
     */
    private String roleType;
    /**
     * 岗位
     */
    private String role;
    /**
     * 岗位代号
     */
    private Integer roleCode;
}

