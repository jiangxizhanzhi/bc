/**
 * 这段代码定义了一个名为 `EvalRecordResultRsp` 的 Java 类，该类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。这个类是 `EvalRecordRsp` 类的子类。以下是对代码的简要分析： 
 * ### 主要功能 
 * - `EvalRecordResultRsp` 类用于表示评估记录的结果响应。 
 * - 它扩展了 `EvalRecordRsp` 类，可能继承了一些通用的评估记录属性。 
 * ### 属性 
 * - **score**: 一个整数类型的变量，表示评估得分。使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - **message**: 一个字符串类型的变量，表示运行错误信息。同样使用了 Lombok 的 `@Getter` 和 `@Setter` 注解。 
 * ### 注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 自动生成所有属性的 getter 方法。 
 * - `@Setter`: 自动生成所有属性的 setter 方法。 
 * - **Swagger 注解**: 
 * - `@ApiModelProperty`: 用于描述 API 中字段的用途和说明。例如，“评估得分” 和 “运行错误信息”。 
 * ### 总结 
 * - `EvalRecordResultRsp` 类主要包含两个属性：评估得分 (`score`) 和运行错误信息 (`message`)。 
 * - 这个类适用于 RESTful API 响应，通过 Swagger 注解可以方便地生成 API 文档。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码。 
 * ### 适用场景 
 * - 在评估系统中，用于返回具体的评估结果和可能的错误信息。 
 * - 可以在 RESTful API 的响应体中使用这个类，返回给客户端评估的具体结果和任何潜在的错误信息。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估记录结果响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordResultRsp extends EvalRecordRsp {
    /**
     * 得分
     */
    @ApiModelProperty("评估得分")
    private Integer score;

    /**
     * 消息
     */
    @ApiModelProperty("运行错误信息")
    private String message;
}
