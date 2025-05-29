package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号算法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `IndexAlgorithm` 的类，位于 `com.cyberpeace.space.service.context.index` 包中。以下是对其功能和结构的简要分析： 
 * ### 功能描述 
 * - **编号算法**：这是一个用于计算某种综合评分或指数的类，主要用于处理与“基础”、“能力”、“设备”、“效率”相关的数值。 
 * - **属性**： 
 * - `base`、`ability`、`equipment` 和 `efficiency` 分别表示不同的评分维度（如基础值、能力评估等）。 
 * - `sum` 表示这些维度的总和。 
 * - `use` 表示某个指标的使用次数。 
 * - **方法**： 
 * - `calSum()` 方法用于计算并更新 `sum` 属性，将 `base`、`ability`、`equipment` 和 `efficiency` 的值相加。 
 * ### 特点 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于 API 文档生成。 
 * - 类的设计较为简单，主要目的是封装与评分相关的逻辑。 
 * ### 总结 
 * 该类是一个简单的数据模型类，结合了 Lombok 和 Swagger 注解，用于管理与评分相关的多个维度，并提供一个计算总和的方法。适合在需要对多维度评分进行处理的场景中使用。 
**/
@Getter
@Setter
public class IndexAlgorithm {

    /**
     * 基础
     *
     * @see Integer
     */
    @ApiModelProperty(value = "基础算法")
    private Integer base = 0;

    /**
     * 能力
     *
     * @see Integer
     */
    @ApiModelProperty(value = "专用能力评估算法")
    private Integer ability = 0;

    /**
     * 设备
     *
     * @see Integer
     */
    @ApiModelProperty(value = "装备评估算法")
    private Integer equipment = 0;

    /**
     * 效率
     *
     * @see Integer
     */
    @ApiModelProperty(value = "防御效能评估算")
    private Integer efficiency = 0;

    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "总数")
    private Integer sum = 0;

    /**
     * 使用
     *
     * @see Integer
     */
    @ApiModelProperty(value = "使用次数")
    private Integer use = 0;

    /**
     * Cal Sum
     *
     * @throws
     */
    public void calSum() {
        this.sum = this.base + this.ability + this.efficiency + this.equipment;
    }
}

