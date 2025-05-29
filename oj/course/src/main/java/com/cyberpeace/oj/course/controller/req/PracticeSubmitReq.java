package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 实践提交请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PracticeSubmitReq` 的类，用于表示实践提交请求体。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`PracticeSubmitReq` 
 * - **继承关系**：实现 `Serializable` 接口 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - **Swagger 注解** 
 * - `@ApiModel`：用于描述整个模型对象。 
 * - `@ApiModelProperty`：用于描述模型中的具体属性。 
 * ### 属性 
 * 1. **practiceType** (Integer) 
 * - 注解： 
 * - `@NotNull`：表示该字段不能为空。 
 * - `@ApiModelProperty`：描述为“习题类型 0=理论基础 2=实操练习”。 
 * - 描述：表示习题类型。 
 * 2. **submitAnswer** (String) 
 * - 注解： 
 * - `@NotBlank`：表示该字段不能为空字符串。 
 * - `@ApiModelProperty`：描述为“提交的答案”。 
 * - 描述：表示提交的答案。 
 * 3. **practiceId** (String) 
 * - 注解： 
 * - `@NotBlank`：表示该字段不能为空字符串。 
 * - `@ApiModelProperty`：描述为“习题编号”。 
 * - 描述：表示习题编号。 
 * 4. **needEvaluate** (Boolean) 
 * - 注解： 
 * - `@ApiModelProperty`：描述为“是否需要判题”。 
 * - 描述：表示是否需要进行判题，默认值为 `false`。 
 * 5. **questionType** (Integer) 
 * - 注解： 
 * - `@ApiModelProperty`：描述为“题型”。 
 * - 描述：表示题型（例如选择题、填空题等）。 
 * ### 总结 
 * - **用途**：`PracticeSubmitReq` 类用于封装用户在提交实践作业时所需的信息，包括习题类型、提交的答案、习题编号以及是否需要判题等。 
 * - **数据验证**：使用了 `@NotNull` 和 `@NotBlank` 注解来确保某些字段不为空。 
 * - **文档生成**：通过 Swagger 注解提供清晰的文档说明，便于前端开发人员理解如何使用该请求体。 
 * 这个类的设计目的是为了方便管理和验证用户提交的实践作业信息，并且提供了详细的注释和描述，有助于后续的维护和扩展。 
**/
@Getter
@Setter
@ApiModel
public class PracticeSubmitReq implements Serializable {

    /**
     * 执业类型
     *
     * @see Integer
     */
    @ApiModelProperty("习题类型 0=理论基础 2=实操练习")
    @NotNull
    private Integer practiceType;

    /**
     * 提交答案
     *
     * @see String
     */
    @ApiModelProperty("提交的答案")
    @NotBlank
    private String submitAnswer;

    /**
     * 实践id
     *
     * @see String
     */
    @ApiModelProperty("习题编号")
    @NotBlank
    private String practiceId;

    /**
     * 需要评估
     *
     * @see Boolean
     */
    @ApiModelProperty("是否需要判题")
    private Boolean needEvaluate = false;

    /**
     * 问题类型
     *
     * @see Integer
     */
    @ApiModelProperty("题型")
    private Integer questionType;

}

