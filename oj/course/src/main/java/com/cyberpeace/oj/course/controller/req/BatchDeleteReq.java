/**
 * 这段代码定义了一个名为 `BatchDeleteReq` 的 Java 类，用于处理批量删除请求。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **类名**: `BatchDeleteReq` 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **继承**: 实现了 `Serializable` 接口 
 * ### 注解 
 * - **`@Getter` 和 `@Setter`**: 来自 Lombok 库，自动生成属性的 getter 和 setter 方法。 
 * - **`@ApiModel`**: Swagger 注解，用于描述这个类作为 API 模型对象。 
 * - **`@ApiModelProperty`**: Swagger 注解，用于描述类中的特定属性。 
 * ### 属性 
 * - **`resourceIds`**: 
 * - 类型: `List<String>` 
 * - 注解: 
 * - **`@NotEmpty`**: 确保列表不为空。 
 * - **`@ApiModelProperty`**: 描述该属性为“资源编号数组”。 
 * ### 功能 
 * - 该类用于接收批量删除请求的数据，特别是包含需要删除的资源 ID 列表。 
 * - 使用了 Lombok 提供的注解来简化代码。 
 * - 通过 Swagger 注解进行文档化，方便生成 API 文档。 
 * ### 总结 
 * - 这是一个简单的数据传输对象（DTO），用于在客户端和服务器之间传递批量删除请求所需的数据。 
 * - 通过注解增强了类的可读性和自动生成了必要的方法，同时支持 Swagger 文档生成。 
**/
package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 批量删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class BatchDeleteReq implements Serializable {

    /**
     * 资源id列表
     *
     * @see List<String>
     */
    @ApiModelProperty("资源编号数组")
    @NotEmpty
    private List<String> resourceIds;

}
