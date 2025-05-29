package com.cyberpeace.cr.crbase.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户id岗位
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `UserIdRole` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.dao.entity` 中。以下是简要分析： 
 * 1. **类的功能**： 
 * 该类用于表示用户 ID 和其对应的岗位信息，主要包含两个字段：`userId`（用户 ID）和 `role`（岗位对象）。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Getter` 和 `@Setter`：自动生成字段的 getter 和 setter 方法。 
 * - `@AllArgsConstructor`：生成一个包含所有字段的构造函数。 
 * 3. **字段说明**： 
 * - `userId`：字符串类型，表示用户的唯一标识。 
 * - `role`：类型为 `CrRole`（推测是一个外部类或枚举），表示用户的岗位。 
 * 4. **设计目的**： 
 * 这个类可能是作为数据传输对象（DTO）或实体类使用，用于在系统中存储和传递用户与岗位的关系。 
 * 总结：这是一个简单的实体类，通过 Lombok 减少了样板代码，专注于描述用户 ID 和岗位之间的关系。 
**/
@Getter
@Setter
@AllArgsConstructor
public class UserIdRole {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 岗位
     */
    private CrRole role;
}

