package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 实践答案上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PracticeAnswerContext` 的 Java 类，用于表示实践答案的上下文信息。以下是该类的详细分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.course.service.bo; 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `package com.cyberpeace.oj.course.service.bo;`：声明了该类所在的包。 
 * - 导入了 Lombok 库的 `Getter` 和 `Setter` 注解，以及 Swagger 注解 `ApiModel` 和 `ApiModelProperty`。 
 * ### 类声明 
 * ```java 
 * @ApiModel 
 * @Getter 
 * @Setter 
 * public class PracticeAnswerContext implements Serializable { 
 * ``` 
 * - `@ApiModel`：标记该类为一个模型，通常用于 Swagger 文档生成。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `implements Serializable`：表明该类可以被序列化，通常用于对象在网络中传输或保存到文件系统。 
 * ### 属性 
 * ```java 
 * @ApiModelProperty("习题类型 0=理论基础 2=实操练习") 
 * private Integer practiceType; 
 * @ApiModelProperty("提交的答案") 
 * private String submitAnswer; 
 * @ApiModelProperty("习题编号") 
 * private String practiceId; 
 * @ApiModelProperty("是否需要判题") 
 * private Boolean needEvaluate = false; 
 * @ApiModelProperty("题型") 
 * private Integer questionType; 
 * ``` 
 * - 每个属性都使用了 `@ApiModelProperty` 注解来描述其用途和可能的值。 
 * - `practiceType`：表示习题类型（理论基础或实操练习）。 
 * - `submitAnswer`：表示用户提交的答案。 
 * - `practiceId`：表示习题编号。 
 * - `needEvaluate`：表示是否需要进行判题，默认值为 `false`。 
 * - `questionType`：表示题型。 
 * ### 总结 
 * - `PracticeAnswerContext` 类主要用于封装与实践题目相关的上下文信息，如习题类型、提交的答案、习题编号、是否需要判题等。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解以简化代码。 
 * - 使用了 Swagger 的注解 `@ApiModel` 和 `@ApiModelProperty` 来生成 API 文档。 
 * 这个类的设计清晰且功能明确，适合用于在线评测系统中的实践题目管理。 
**/
@Getter
@Setter
@ApiModel
public class PracticeAnswerContext implements Serializable {

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题类型 0=理论基础 2=实操练习")
	private Integer practiceType;

	/**
	 * 提交答案
	 *
	 * @see String
	 */
	@ApiModelProperty("提交的答案")
	private String submitAnswer;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
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

