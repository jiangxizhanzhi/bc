package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * eval record index 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordIndexContext` 的 Java 类，该类用于表示评估记录索引的上下文。以下是对其主要特性和功能的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **继承**：实现了 `Serializable` 接口，表明这个类的对象可以序列化。 
 * - **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **resourceId** (String)：资源ID，表示当前指标项的唯一标识符。 
 * - **type** (Integer)：类型，表示三级指标项的评分类型。 
 * - **score** (Integer)：得分，表示当前指标项的评分值（可选）。 
 * - **variables** (List<EvalRecordVariableContext>)：关联变量项列表，表示与当前指标项相关的变量信息。 
 * - **indexes** (List<EvalRecordIndexContext>)：关联聚合项列表，表示与当前指标项相关的其他指标项信息。 
 * - **specialist** (EvalRecordSpecialistContext)：专家评分配置，表示与当前指标项相关的专家评分信息。 
 * ### Swagger 注释 
 * - 使用了 Swagger 注释 (`@ApiModelProperty`) 来描述每个字段的用途和是否为必填项。这些注释有助于生成 API 文档，并使 API 更加清晰易懂。 
 * ### 总结 
 * `EvalRecordIndexContext` 类主要用于存储和传递评估记录中的索引上下文信息。它包含了多个属性，如资源 ID、类型、得分、关联变量项、关联聚合项以及专家评分配置等。通过使用 Lombok 和 Swagger 注释，该类在保持简洁性的同时提供了丰富的元数据信息，便于在 API 开发中使用。 
**/
@Getter
@Setter
public class EvalRecordIndexContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
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
    private List<EvalRecordVariableContext> variables;

    /**
     * 指标
     */
    @ApiModelProperty("指标项下关联聚合项")
    private List<EvalRecordIndexContext> indexes;

    /**
     * 专家
     */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}

