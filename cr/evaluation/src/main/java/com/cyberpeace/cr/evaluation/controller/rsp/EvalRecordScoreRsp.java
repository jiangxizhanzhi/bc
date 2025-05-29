package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估记录得分响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordScoreRsp` 的 Java 类，用于表示评估记录得分的响应。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **导入**: 引入了 `io.swagger.annotations.ApiModelProperty` 和 `lombok.Getter`、`lombok.Setter` 注解。 
 * - **注解**: 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 类描述 
 * - 类注释说明了这是一个用于表示评估记录得分响应的类。 
 * - 作者是 SN，日期是 2024年11月8日。 
 * ### 属性 
 * - **name**: 名称，字符串类型。 
 * - **resourceId**: 资源 ID，字符串类型。 
 * - **level**: 水平，整数类型。 
 * - **type**: 类型，整数类型，表示 3 级指标项评分类型。 
 * - **score**: 得分，整数类型，表示当前指标获取得分。 
 * - **thirdLevelType**: 第三级类型，整数类型，表示 3 级指标项评分选项。 
 * - **children**: 子级列表，类型为 `List<EvalRecordScoreRsp>`，表示包含子级的列表。 
 * ### 特点 
 * - 使用 Swagger 注解 `@ApiModelProperty` 来描述每个属性的含义。 
 * - 类中的属性都有相应的注释，解释了每个字段的用途。 
 * - 使用 Lombok 注解简化了代码，避免了手动编写 getter 和 setter 方法。 
 * ### 总结 
 * 这个类主要用于构建评估记录得分的响应对象，包含了评估记录的基本信息以及可能的子级列表。通过使用 Lombok 和 Swagger 注解，使得代码更加简洁且具有良好的可读性。 
**/
@Getter
@Setter
public class EvalRecordScoreRsp  {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * 水平
	 */
	@ApiModelProperty(value = "级别")
	private Integer level;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "3级指标项评分类型")
	private Integer type;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "当前指标获取得分")
	private Integer score;

	/**
	 * 第三级类型
	 */
	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalRecordScoreRsp> children;

}
