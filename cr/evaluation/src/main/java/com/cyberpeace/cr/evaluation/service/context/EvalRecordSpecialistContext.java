package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * EVAL 记录专家背景
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordSpecialistContext` 的 Java 类，该类用于表示评估记录中的专家背景信息。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于生成 Swagger 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：简化 getter 和 setter 方法的生成。 
 * - `javax.validation.constraints.NotNull`：用于验证字段是否不能为空。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * ### 类注释 
 * - 该类用于记录评估中的专家背景信息。 
 * - 作者是 `SN`，日期为 `2024年11月8日`。 
 * ### 类属性 
 * - **type**：整数类型，表示数据类型。 
 * - 注解 `@ApiModelProperty` 说明了该字段的用途：当作为三级指标时，`value` 字段不为空；可取值为百分比（1）、周期（2）或延时（3）。 
 * - **property**：整数类型，表示数据属性。 
 * - 注解 `@ApiModelProperty` 简单描述了该字段的用途。 
 * - **value**：整数类型，表示具体的值。 
 * - 注解 `@ApiModelProperty` 描述了该字段的用途。 
 * ### Lombok 注解 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法，减少了样板代码。 
 * ### 总结 
 * 该类主要用于存储评估记录中与专家背景相关的数据，包括数据类型、数据属性和具体数值。通过使用 Lombok 的注解，减少了手动编写 getter 和 setter 方法的工作量，并且通过 Swagger 注解使得该类在文档生成时能够被正确描述。 
**/
@Getter
@Setter
public class EvalRecordSpecialistContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 类型
     */
    @ApiModelProperty("数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3")
    private Integer type;

    /**
     * 配置
     */
    @ApiModelProperty("数据属性")
    private Integer property;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private Integer value;
}

