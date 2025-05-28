/**
 * 这段代码定义了一个名为 `EvalRecordResultVariableRsp` 的 Java 类，用于表示评估记录结果变量的响应。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：该类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **导入**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档注解，描述字段含义。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：接口，表明该类可以序列化。 
 * ### 类属性 
 * - **resourceId**：资源 ID，字符串类型，通过 `@ApiModelProperty` 注解描述为 "ID"。 
 * - **name**：变量项名称，字符串类型，通过 `@ApiModelProperty` 注解描述为 "变量项名称"。 
 * - **formatAlgorithm**：格式化算法信息，类型为 `EvalRecordResultAlgorithmRsp`，通过 `@ApiModelProperty` 注解描述为 "格式化算法信息"。 
 * - **matchAlgorithm**：匹配算法信息，类型为 `EvalRecordResultAlgorithmRsp`，通过 `@ApiModelProperty` 注解描述为 "匹配算法信息"。 
 * - **markAlgorithm**：评分算法信息，类型为 `EvalRecordResultAlgorithmRsp`，通过 `@ApiModelProperty` 注解描述为 "评分算法信息"。 
 * ### Lombok 注解 
 * - `@Getter`：自动生成所有属性的 getter 方法。 
 * - `@Setter`：自动生成所有属性的 setter 方法。 
 * ### 实现的接口 
 * - `Serializable`：该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化存储。 
 * ### 总结 
 * - **用途**：`EvalRecordResultVariableRsp` 类用于封装评估记录结果中的变量信息，包括资源 ID、名称以及与之相关的三种算法信息（格式化、匹配和评分）。 
 * - **注解**：使用了 Swagger 注解来生成文档，并且使用了 Lombok 注解来简化代码，减少样板代码。 
 * 这个类的设计简洁明了，便于理解和维护。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估记录结果变量响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordResultVariableRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("变量项名称")
    private String name;

    /**
     * 格式算法
     */
    @ApiModelProperty("格式化算法信息")
    private EvalRecordResultAlgorithmRsp formatAlgorithm;

    /**
     * 匹配算法
     */
    @ApiModelProperty("匹配算法信息")
    private EvalRecordResultAlgorithmRsp matchAlgorithm;

    /**
     * 标记算法
     */
    @ApiModelProperty("评分算法信息")
    private EvalRecordResultAlgorithmRsp markAlgorithm;
}
