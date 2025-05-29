package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * eval record index ref 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordIndexRefContext` 的 Java 类，该类用于表示评估记录索引引用的上下文。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**：`EvalRecordIndexRefContext` 
 * - **实现接口**：`Serializable`（表示该类的对象可以序列化） 
 * ### 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解，表明该类的所有属性都有对应的 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 `@ApiModelProperty` 来描述属性的含义和是否必填。 
 * ### 属性 
 * 1. **resourceId** 
 * - **类型**：`String` 
 * - **描述**：资源 ID，标识具体的指标项。 
 * - **注解**：`@ApiModelProperty(value = "指标项ID", required = true)` 表示这是一个必填字段。 
 * 2. **type** 
 * - **类型**：`Integer` 
 * - **描述**：类型，具体指三级指标项的评分类型。 
 * - **注解**：`@ApiModelProperty(value = "三级指标项评分类型", required = true)` 表示这是一个必填字段。 
 * 3. **score** 
 * - **类型**：`Integer` 
 * - **描述**：得分，表示具体的评分。 
 * - **注解**：`@ApiModelProperty(value = "评分", required = false)` 表示这是一个可选字段。 
 * 4. **variables** 
 * - **类型**：`List<EvalRecordIndexContext>` 
 * - **描述**：关联的变量项列表，表示该指标项下关联的变量。 
 * - **注解**：`@ApiModelProperty("指标项下关联变量项")` 表示这是一个关联变量项的列表。 
 * 5. **specialist** 
 * - **类型**：`EvalRecordSpecialistContext` 
 * - **描述**：专家评分配置，表示指标项的专家评分设置。 
 * - **注解**：`@ApiModelProperty("指标项专家评分配置")` 表示这是一个专家评分配置对象。 
 * ### 总结 
 * `EvalRecordIndexRefContext` 类是一个用于表示评估记录索引引用上下文的数据模型类，包含了一些重要的属性如资源 ID、类型、得分、关联变量项和专家评分配置。通过这些属性，该类能够有效地描述一个评估记录索引的相关信息。 
**/
@Getter
@Setter
public class EvalRecordIndexRefContext implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty(value = "指标项ID", required = true)
    private String resourceId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "三级指标项评分类型", required = true)
    private Integer type;

    /**
     * 得分
     */
    @ApiModelProperty(value = "评分", required = false)
    private Integer score;

    /**
     * 变量
     */
    @ApiModelProperty("指标项下关联变量项")
    private List<EvalRecordIndexContext> variables;

    /**
     * 专家
     */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}

