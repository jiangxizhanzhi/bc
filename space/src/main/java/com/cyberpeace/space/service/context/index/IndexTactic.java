package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号战法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `IndexTactic` 的 Java 类，位于包 `com.cyberpeace.space.service.context.index` 下。以下是代码的主要分析和总结： 
 * ### 主要功能 
 * - **类的作用**：该类表示一个编号战法的上下文对象，用于存储与战法相关的统计信息。 
 * - **属性**： 
 * - `attack`：表示攻击战法库的数量，默认值为 0。 
 * - `defense`：表示防御战法库的数量，默认值为 0。 
 * - `use`：表示战法库的使用次数，默认值为 0。 
 * - `sum`：表示攻击和防御战法库的总和，默认值为 0。 
 * - `addedToday`：表示今天新增的战法库数量，默认值为 0。 
 * - `updatedToday`：表示今天修改的战法库数量，默认值为 0。 
 * ### 方法 
 * - **`calSum()`**：计算并设置 `sum` 属性为 `attack` 和 `defense` 的总和。 
 * ### 特性 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 自动生成了所有字段的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty` 用于描述每个字段的用途，便于 API 文档生成。 
 * ### 总结 
 * 这是一个简单的数据模型类，主要用于存储和管理战法相关的统计数据，并提供了基本的计算功能（如 `calSum`）。代码结构清晰，注释详细，符合良好的编码规范。 
**/
@Getter
@Setter
public class IndexTactic {

    /**
     * 攻击
     *
     * @see Integer
     */
    @ApiModelProperty(value = "攻击战法库")
    private Integer attack = 0;

    /**
     * 防御
     *
     * @see Integer
     */
    @ApiModelProperty(value = "防御战法库")
    private Integer defense = 0;

    /**
     * 使用
     *
     * @see Integer
     */
    @ApiModelProperty(value = "战法库使用次数")
    private Integer use = 0;

    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "总数:")
    private Integer sum = 0;

    /**
     * 今天添加
     *
     * @see Integer
     */
    @ApiModelProperty(value = "今日新增战法库")
    private Integer addedToday = 0;

    /**
     * 今天更新
     *
     * @see Integer
     */
    @ApiModelProperty(value = "今日修改战法库")
    private Integer updatedToday = 0;

    /**
     * Cal Sum
     *
     * @throws
     */
    public void calSum() {
        this.sum = this.attack + this.defense;
    }
}

