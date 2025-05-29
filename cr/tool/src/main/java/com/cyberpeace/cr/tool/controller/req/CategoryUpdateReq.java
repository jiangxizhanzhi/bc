package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * 类别更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CategoryUpdateReq` 的 Java 类，它是 `CategoryAddReq` 类的一个子类。这个类用于表示类别更新请求的数据模型，并且使用了 Lombok、Swagger 和 JSR 380 (Bean Validation) 注解来简化开发和增强功能。 
 * ### 主要特性： 
 * 1. **继承关系**： 
 * - `CategoryUpdateReq` 继承自 `CategoryAddReq`。这意味着它可能包含了从父类继承的一些字段和方法。 
 * 2. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：这两个注解由 Lombok 提供，会自动生成类中所有字段的 getter 和 setter 方法。 
 * 3. **Swagger 注解**： 
 * - `@ApiModel`：这个注解用于描述整个数据模型，通常在 Swagger 文档中展示。 
 * - `@ApiModelProperty`：这个注解用于描述特定字段，可以在 Swagger UI 中看到这些描述。 
 * 4. **JSR 380 注解（Bean Validation）**： 
 * - `@NotBlank`：这是一个约束注解，用于确保 `resourceId` 字段不为空或仅包含空白字符。 
 * ### 主要字段： 
 * - `resourceId`：字符串类型，用于表示资源 ID。根据 `@NotBlank` 注解，这个字段不能为空或仅包含空白字符。 
 * ### 总结： 
 * - `CategoryUpdateReq` 类是一个数据传输对象（DTO），用于接收更新类别请求的数据。 
 * - 它包含一个必填的 `resourceId` 字段，确保客户端在发送更新请求时必须提供有效的资源 ID。 
 * - 该类通过 Lombok 和 Swagger 注解增强了可读性和文档生成能力。 
**/
@Getter
@Setter
@ApiModel
public class CategoryUpdateReq extends CategoryAddReq {
    /**
     * 资源id
     */
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;

}

