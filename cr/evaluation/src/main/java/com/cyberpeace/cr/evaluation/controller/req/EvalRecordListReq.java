package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 评估记录列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordListReq` 的 Java 类，该类位于包 `com.cyberpeace.cr.evaluation.controller.req` 中。这是一个用于处理评估记录列表请求的请求体类，并继承自 `PageReqBase` 类。以下是对这段代码的详细分析： 
 * ### 包声明和导入 
 * - **包声明**：`package com.cyberpeace.cr.evaluation.controller.req;` 
 * - 表示这个类属于 `com.cyberpeace.cr.evaluation.controller.req` 包。 
 * - **导入语句**： 
 * - `import com.cyberpeace.common.model.PageReqBase;`：引入了 `PageReqBase` 类。 
 * - `import io.swagger.annotations.ApiModelProperty;`：引入了 Swagger 注解，用于生成 API 文档。 
 * - `import lombok.Getter;` 和 `import lombok.Setter;`：引入了 Lombok 注解，用于简化 getter 和 setter 方法的生成。 
 * ### 类定义 
 * - **类名**：`EvalRecordListReq` 
 * - 继承自 `PageReqBase` 类。 
 * - 使用 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **taskId** 
 * - 类型：`String` 
 * - 注解： 
 * - `@NotNull`：表示该字段不能为空。 
 * - `@ApiModelProperty(value = "房间Id")`：用于 Swagger 文档生成，描述该字段为“房间Id”。 
 * - **status** 
 * - 类型：`Integer` 
 * - 注解： 
 * - `@ApiModelProperty(value = "状态查询")`：用于 Swagger 文档生成，描述该字段为“状态查询”。 
 * ### 总结 
 * - 这是一个用于处理评估记录列表请求的请求体类。 
 * - 它包含两个主要属性：`taskId` 和 `status`。 
 * - `taskId` 是一个必须填写的字符串字段，用于标识任务。 
 * - `status` 是一个可选的整数字段，用于查询状态。 
 * - 通过 Swagger 注解，可以生成详细的 API 文档。 
 * - 使用 Lombok 注解简化了 getter 和 setter 方法的生成。 
 * 这个类主要用于前端或其他服务端组件向后端发送请求时携带相关参数。 
**/
@Getter
@Setter
public class EvalRecordListReq extends PageReqBase {
    /**
     * 任务id
     */
    @NotNull
    @ApiModelProperty("房间Id")
    private String taskId;

    /**
     * 状态
     */
    @ApiModelProperty("状态查询")
    private Integer status;
}

