package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评论是喜欢请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CommentIsLikeReq` 的 Java 类，该类用于表示一个请求体（request body），主要用于处理与评论点赞相关的操作。下面是对此代码的简要分析： 
 * 1. **包声明**： 
 * - `com.cyberpeace.oj.ojbase.controller.req;`：此代码位于 `com.cyberpeace.oj.ojbase.controller.req` 包中。 
 * 2. **导入语句**： 
 * - 导入了 Lombok 库中的 `@Getter` 和 `@Setter` 注解，以自动生成 getter 和 setter 方法。 
 * - 导入了 Swagger 注解 `@ApiModel` 和 `@ApiModelProperty`，用于生成 API 文档。 
 * - 导入了 `javax.validation.constraints.NotBlank` 和 `javax.validation.constraints.NotNull` 注解，用于验证数据的有效性。 
 * - `java.io.Serializable` 接口，使对象可以序列化。 
 * 3. **类声明**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `@ApiModel`：用于标识这个类是一个模型对象，并可用来生成 API 文档。 
 * - `implements Serializable`：表明这个类的对象可以被序列化，便于在网络上传输或持久化存储。 
 * 4. **字段声明**： 
 * - `@NotNull private Boolean isLike;`：表示是否点赞的布尔值，必须提供且不能为 null。 
 * - `@NotBlank private String resourceId;`：表示资源编号，不能为空字符串，且至少有一个非空白字符。 
 * 5. **文档注释**： 
 * - 类级别的 Javadoc 提供了关于这个类的基本信息。 
 * - 字段级别的 Javadoc 对每个字段进行了简短说明，包括它们的数据类型和用途。 
 * 总结： 
 * - `CommentIsLikeReq` 类用于接收客户端发送的点赞请求，包含两个字段：`isLike` 表示是否点赞，`resourceId` 表示资源编号。 
 * - 使用了 Lombok 来简化 getter 和 setter 方法的编写，使用了 Swagger 注解来生成 API 文档，使用了 JSR 380 (Bean Validation) 注解来确保输入的有效性。 
 * - 此类实现了 `Serializable` 接口，支持对象的序列化。 
**/
@Getter
@Setter
@ApiModel
public class CommentIsLikeReq implements Serializable {

    /**
     * 就像
     *
     * @see Boolean
     */
    @ApiModelProperty("是否点赞")
    @NotNull
    private Boolean isLike;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
    @NotBlank
    private String resourceId;

}

