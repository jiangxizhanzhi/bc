package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估变量图响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableGraphRsp` 的 Java 类，用于表示评估变量图的响应。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于Swagger文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成getter和setter方法。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * ### 类说明 
 * - **类名**：`EvalVariableGraphRsp` 
 * - **实现接口**：`Serializable` 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * ### 类属性 
 * - **时间 (`time`)**： 
 * - 类型：`List<Date>` 
 * - 注释：节点时间点 
 * - Swagger注解：`@ApiModelProperty("时间节点")` 
 * - **值 (`value`)**： 
 * - 类型：`List<Integer>` 
 * - 注释：格式化算法生成的值 
 * - Swagger注解：`@ApiModelProperty("格式化算法生成值")` 
 * - **匹配 (`isMatched`)**： 
 * - 类型：`List<Boolean>` 
 * - 注释：是否通过模型检测算法 
 * - Swagger注解：`@ApiModelProperty("是否通过模型检测算法")` 
 * ### 特性 
 * - **Lombok注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的getter和setter方法，简化了代码。 
 * ### 总结 
 * 这个类主要用于封装评估变量图的响应数据，包含三个列表属性：时间、值和匹配结果。通过Swagger注解，这些属性可以在API文档中清晰地展示出来。使用Lombok注解减少了样板代码，提高了代码的可读性和简洁性。 
**/
@Getter
@Setter
public class EvalVariableGraphRsp implements Serializable {
    /**
     * 时间
     */
    @ApiModelProperty("时间节点")
    private List<Date> time;

    /**
     * 值
     */
    @ApiModelProperty("格式化算法生成值")
    private List<Integer> value;

    /**
     * 匹配
     */
    @ApiModelProperty("是否通过模型检测算法")
    private List<Boolean> isMatched;
}

