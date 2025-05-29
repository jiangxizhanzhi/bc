package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 练习解决了 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PracticeSolvedBo` 的 Java 类，该类主要用于表示用户在某个练习中提交的答案及其结果。以下是对这段代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.service.bo; 
 * ``` 
 * - 这行代码指定了该类所在的包（`com.cyberpeace.oj.course.service.bo`）。 
 * ### 导入的库 
 * ```java 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - `io.swagger.annotations.ApiModel`: 用于描述这个类是一个模型。 
 * - `io.swagger.annotations.ApiModelProperty`: 用于描述类中的属性。 
 * - `lombok.Getter`, `lombok.Setter`: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`: 表示这个类可以序列化。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * @ApiModel 
 * public class PracticeSolvedBo implements Serializable { 
 * ``` 
 * - `@Getter`, `@Setter`: Lombok 注解，自动生成所有字段的 getter 和 setter 方法。 
 * - `@ApiModel`: Swagger 注解，用于描述这个类作为一个模型。 
 * - `Serializable`: 接口，表明这个类可以被序列化。 
 * ### 字段 
 * ```java 
 * private String userAnswer; 
 * private Boolean solved; 
 * private String resourceId; 
 * private Double score; 
 * ``` 
 * - `userAnswer`: 用户提交的答案。 
 * - `solved`: 表示用户是否正确回答了问题。 
 * - `resourceId`: 练习的唯一标识符。 
 * - `score`: 用户在这个练习中获得的分数。 
 * ### 字段注释 
 * 每个字段都有对应的 Swagger 注解 `@ApiModelProperty`，用于生成 API 文档。 
 * ### 总结 
 * - `PracticeSolvedBo` 类用于封装用户在练习中提交的答案以及其结果（如是否正确、得分等）。 
 * - 使用了 Lombok 来简化代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * - 类实现了 `Serializable` 接口，使其可以在网络上传输或持久化存储。 
 * - 使用 Swagger 注解来帮助生成 API 文档。 
**/
@Getter
@Setter
@ApiModel
public class PracticeSolvedBo implements Serializable {

	/**
	 * 用户答案
	 *
	 * @see String
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否回答正确")
	private Boolean solved;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("得分")
	private Double score;

}

