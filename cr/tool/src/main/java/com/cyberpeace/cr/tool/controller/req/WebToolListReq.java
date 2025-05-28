/**
 * 这段代码定义了一个名为 `WebToolListReq` 的 Java 类，该类用于表示获取 Web 工具列表的请求参数。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.tool.controller.req` 
 * - **继承关系**：`WebToolListReq` 继承自 `PageReqBase`，后者可能是用于分页请求的基础类。 
 * - **注解**： 
 * - `@ApiModel`：标识这是一个 Swagger API 模型，用于生成文档。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **category**（分类）：`String` 类型，表示工具的分类。 
 * - **platforms**（平台）：`String` 类型，表示支持的平台。 
 * - **cracked**（授权）：`String` 类型，表示是否为破解版。 
 * - **type**（类型）：`String` 类型，表示工具的类型。 
 * - **tag**（标签）：`String` 类型，表示工具的标签。 
 * ### 总结 
 * - `WebToolListReq` 是一个用于 Web 工具列表查询的请求体类。 
 * - 它继承了 `PageReqBase` 类，可能用于处理分页相关的逻辑。 
 * - 通过 Swagger 注解，可以自动生成 API 文档，方便前端开发人员理解和使用。 
 * - 类中的属性用于描述工具的不同方面，如分类、平台、授权状态等。 
 * 这个类的主要目的是封装前端传递过来的查询条件，并提供给后端进行数据检索。 
**/
package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Web工具列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class WebToolListReq extends PageReqBase {

    /**
     * 类别
     */
    @ApiModelProperty("分类")
    private String category;

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    private String platforms;

    /**
     * 破获
     */
    @ApiModelProperty("授权")
    private String cracked;

    /**
     * 类型
     */
    private String type;
    @ApiModelProperty("标签")
    private String tag;
}
