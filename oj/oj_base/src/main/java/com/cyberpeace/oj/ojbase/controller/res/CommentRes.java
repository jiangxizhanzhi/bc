/**
 * 这段代码定义了一个名为 `CommentRes` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示课程评论的信息，并且使用了 Lombok 库来简化 getter 和 setter 方法的生成。此外，还使用了 Swagger 注解来描述 API 模型。 
 * ### 主要特点和功能： 
 * 1. **继承与实现**: 
 * - `CommentRes` 实现了 `Serializable` 接口，这使得对象可以序列化，便于在网络上传输或持久化到磁盘上。 
 * 2. **Lombok 注解**: 
 * - `@Getter` 和 `@Setter` 注解自动生成所有字段的 getter 和 setter 方法。 
 * 3. **Swagger 注解**: 
 * - `@ApiModel` 注解用于描述整个模型，`@ApiModelProperty` 注解用于描述每个字段的用途。 
 * 4. **字段**: 
 * - `avatar`: 用户头像字符串。 
 * - `resourceId`: 评论资源的唯一标识符。 
 * - `businessId`: 业务编号，关联课程或习题。 
 * - `themeName`: 评论的主题名称。 
 * - `userName`: 发表评论的用户名。 
 * - `comment`: 评论的具体内容。 
 * - `businessType`: 评论的类型，用整数表示。 
 * - `createTime`: 评论创建的时间。 
 * - `parentId`: 评论的父评论 ID，用于表示评论的层次结构。 
 * - `isLike`: 表示当前用户是否已经点赞。 
 * - `likesCount`: 点赞的数量。 
 * ### 总结： 
 * `CommentRes` 类是一个数据传输对象（DTO），它包含了课程评论的所有必要信息，并通过 Swagger 注解方便了 API 文档的生成。通过 Lombok 的注解，减少了样板代码，提高了代码的可读性和维护性。 
**/
package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("课程评论信息")
public class CommentRes implements Serializable {

    /**
     * 化身
     *
     * @see String
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("评论资源编号")
    private String resourceId;

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("资源编号 课程、习题")
    private String businessId;

    /**
     * 主题名称
     *
     * @see String
     */
    @ApiModelProperty("评论主题名称")
    private String themeName;

    /**
     * 用户名称
     *
     * @see String
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 评论
     *
     * @see String
     */
    @ApiModelProperty("评论内容")
    private String comment;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("评论类型")
    private Integer businessType;

    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 父母id
     *
     * @see String
     */
    private String parentId;

    /**
     * 就像
     *
     * @see Boolean
     */
    @ApiModelProperty("是否被点赞")
    private Boolean isLike;

    /**
     * 喜欢计数
     *
     * @see Integer
     */
    @ApiModelProperty("被点赞数量")
    private Integer likesCount;

}
