/**
 * 这段代码定义了一个名为 `CommentLike` 的 Java 类，该类继承自 `BaseTable`。这个类表示一个“评论赞”的实体，并且使用了 MyBatis-Plus 框架来简化数据库操作。以下是详细的分析： 
 * ### 类和注解 
 * - **包声明**：`com.cyberpeace.oj.ojbase.dao.entity` 
 * - **导入的类**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 
 * - `com.baomidou.mybatisplus.annotation.TableId` 
 * - `com.baomidou.mybatisplus.annotation.TableName` 
 * - `com.cyberpeace.common.model.BaseTable` 
 * - Lombok 的 `@Getter` 和 `@Setter` 
 * ### 类定义 
 * - **注解**： 
 * - `@TableName("oj_comment_like")`：指定该类对应的数据库表名为 `oj_comment_like`。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，用于自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **resourceId** (`String`)：资源ID，使用 `@TableId` 注解指定为 UUID 类型，作为主键。 
 * - **userId** (`String`)：用户ID。 
 * - **commentId** (`String`)：评论ID。 
 * ### 总结 
 * - 这个类表示一个“评论赞”实体，包含资源ID、用户ID和评论ID三个属性。 
 * - 使用 MyBatis-Plus 的注解来映射数据库表和字段。 
 * - 继承了 `BaseTable` 类，可能包含一些通用的数据库操作方法或属性。 
 * - Lombok 注解减少了样板代码，自动提供了 getter 和 setter 方法。 
 * 这个类的主要目的是为了与数据库中的 `oj_comment_like` 表进行交互，可以用于创建、读取、更新和删除操作。 
**/
package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 评论赞
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_comment_like")
public class CommentLike extends BaseTable {

    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 用户id
     *
     * @see String
     */
    private String userId;

    /**
     * 评论id
     *
     * @see String
     */
    private String commentId;

}
