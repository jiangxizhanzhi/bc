package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评论请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CommentReq` 的 Java 类，用于表示评论请求体。以下是该类的详细分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.ojbase.controller.req; 
 * ``` 
 * - 这个包名表明该类位于 `com.cyberpeace.oj.ojbase.controller.req` 包下。 
 * ### 导入的类和注解 
 * ```java 
 * import java.io.Serializable; 
 * import javax.validation.constraints.NotBlank; 
 * import javax.validation.constraints.NotNull; 
 * import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode; 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `Serializable`：用于实现序列化接口。 
 * - `@NotBlank` 和 `@NotNull`：用于验证字段不能为空。 
 * - `OjBusinessErrorCode`：自定义错误码常量类。 
 * - `@ApiModel` 和 `@ApiModelProperty`：用于 Swagger 文档生成。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类定义 
 * ```java 
 * @ApiModel("资源评论信息") 
 * @Getter 
 * @Setter 
 * public class CommentReq implements Serializable { 
 * ``` 
 * - `@ApiModel`：定义了这个类在 Swagger 文档中的描述。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：表明这个类可以序列化。 
 * ### 字段定义 
 * ```java 
 * @ApiModelProperty(value = "评论内容") 
 * @NotBlank(message = OjBusinessErrorCode.MESSAGE_CONTENT_IS_NULL) 
 * private String content; 
 * @ApiModelProperty("主题信息 课时笔记=课程名称/课时名称 课程笔记=课程名称 实操习题笔记=实操题名称") 
 * @NotBlank 
 * private String themeName; 
 * @ApiModelProperty("资源编号 课程 课时 练习") 
 * @NotBlank 
 * private String businessId; 
 * @ApiModelProperty("资源评论编号") 
 * private String rootId; 
 * @ApiModelProperty("被回复评论编号") 
 * private String replyId; 
 * @ApiModelProperty("评论资源类型 1=课程 2=课时 3=实操") 
 * @NotNull 
 * private Integer businessType; 
 * ``` 
 * - `content`：评论内容，不能为空。 
 * - `themeName`：主题信息，不能为空。 
 * - `businessId`：资源编号，不能为空。 
 * - `rootId`：根评论编号，可为空。 
 * - `replyId`：回复评论编号，可为空。 
 * - `businessType`：评论资源类型，必须为整数且不能为空。 
 * ### 总结 
 * - `CommentReq` 类用于表示评论请求体，包含评论的基本信息和关联信息。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来简化代码。 
 * - 使用了 Swagger 的 `@ApiModel` 和 `@ApiModelProperty` 注解来生成 API 文档。 
 * - 字段通过 `@NotBlank` 和 `@NotNull` 注解进行了非空校验。 
 * - 实现了 `Serializable` 接口，支持对象序列化。 
**/
@Getter
@Setter
@ApiModel("资源评论信息")
public class CommentReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = OjBusinessErrorCode.MESSAGE_CONTENT_IS_NULL)
    private String content;

    /**
     * 主题名称
     *
     * @see String
     */
    @ApiModelProperty("主题信息 课时笔记=课程名称/课时名称 课程笔记=课程名称 实操习题笔记=实操题名称")
    @NotBlank
    private String themeName;

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("资源编号 课程 课时 练习")
    @NotBlank
    private String businessId;

    /**
     * 根id
     *
     * @see String
     */
    @ApiModelProperty("资源评论编号")
    private String rootId;

    /**
     * 答id
     *
     * @see String
     */
    @ApiModelProperty("被回复评论编号")
    private String replyId;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("评论资源类型 1=课程 2=课时 3=实操")
    @NotNull
    private Integer businessType;

}

