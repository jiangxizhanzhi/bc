/**
 * 这段代码定义了一个名为 `ToolUpdateReq` 的类，该类用于处理工具更新的请求体。以下是对代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.tool.controller.req` 
 * - **类名**: `ToolUpdateReq` 
 * - **继承**: 继承自 `ToolAddReq` 
 * ### 注解 
 * - **`@ApiModel`**: 表示这个类是一个模型对象，通常用于Swagger文档生成。 
 * - **`@Getter` 和 `@Setter`**: Lombok注解，自动生成属性的getter和setter方法。 
 * ### 属性 
 * - **`resourceId`**: 
 * - **注释**: "资源id" 
 * - **类型**: `String` 
 * - **约束**: 必须非空（使用了 `@NotBlank` 注解） 
 * ### 总结 
 * - `ToolUpdateReq` 类主要用于接收工具更新请求的数据。 
 * - 它包含一个必填的 `resourceId` 字段，用于标识需要更新的资源。 
 * - 该类继承自 `ToolAddReq`，可能是因为更新操作与添加操作有一些共用的字段或逻辑。 
 * ### 可能的改进点 
 * - 如果 `ToolUpdateReq` 中没有其他新增字段，可以考虑直接在 `ToolAddReq` 中添加 `resourceId` 字段，以减少类的数量。 
 * - 可以增加更多的验证注解，确保 `resourceId` 符合特定的格式要求（如长度、字符集等）。 
 * 整体而言，这段代码简洁明了，符合RESTful API的设计规范。 
**/
package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 工具更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class ToolUpdateReq extends ToolAddReq{
    /**
     * 资源id
     */
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;
}
