package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 实践纸评估博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PracticePaperEvaluateBo` 的 Java 类，该类用于表示实践纸评估的数据模型。以下是对这个类的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入的库**： 
 * - `java.io.Serializable`：用于实现序列化接口。 
 * - `java.util.Date`：用于表示日期时间。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于生成 Swagger 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成 getter 和 setter 方法。 
 * ### 类说明 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **创建日期**：2024年11月8日 
 * ### 类属性 
 * - **name**：字符串类型，表示名称。 
 * - **username**：字符串类型，表示用户名。 
 * - **userId**：字符串类型，表示用户ID。 
 * - **organizationName**：字符串类型，表示组织名称。 
 * - **submitState**：布尔类型，默认值为 `false`，表示提交状态。 
 * - **submitTime**：`Date` 类型，表示提交时间。 
 * - **accuracy**：字符串类型，表示准确性（如理论题正确率）。 
 * - **level**：整数类型，表示评分等级。 
 * - **corrector**：字符串类型，表示校正者。 
 * - **correctTime**：`Date` 类型，表示批改时间。 
 * ### 特点 
 * - 使用了 Lombok 库来简化代码，自动实现了 getter 和 setter 方法。 
 * - 使用了 Swagger 注解来生成 API 文档。 
 * - 所有属性都通过 `@ApiModelProperty` 注解进行了描述，便于生成文档。 
 * - 类实现了 `Serializable` 接口，支持对象的序列化操作。 
 * ### 总结 
 * `PracticePaperEvaluateBo` 类是一个数据传输对象（DTO），用于封装实践纸评估的相关信息。该类具有良好的可读性和可维护性，并且通过 Lombok 和 Swagger 注解提升了开发效率和文档生成的便利性。 
**/
@Getter
@Setter
@ApiModel
public class PracticePaperEvaluateBo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 提交状态
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("提交状态")
	private Boolean submitState=false;

	/**
	 * 提交时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("提交时间")
	private Date submitTime;

	/**
	 * 准确性
	 *
	 * @see String
	 */
	@ApiModelProperty("理论题正确率")
	private String accuracy;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评分等级")
	private Integer level;

	/**
	 * 校正者
	 *
	 * @see String
	 */
	@ApiModelProperty("批改人")
	private String corrector;

	/**
	 * 正确时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("批改时间")
	private Date correctTime;

}

