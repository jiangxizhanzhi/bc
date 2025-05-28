/**
 * 这段代码定义了一个名为 `EvalSystemResultRsp` 的 Java 类，用于表示评估系统的结果响应。以下是对该类的简要分析： 
 * ### 类名和包 
 * - **包**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **类名**: `EvalSystemResultRsp` 
 * ### 类的主要特性 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Jackson 库的 `@JsonInclude(JsonInclude.Include.NON_NULL)` 注解，确保只有非空字段会被序列化到 JSON。 
 * ### 字段 
 * - `name`: 评估系统的名称。 
 * - `resourceId`: 资源的唯一标识符。 
 * - `exeCycle`: 执行的循环次数。 
 * - `firstScore`, `secondScore`, `thirdScore`, `fourthScore`, `fifthScore`: 分别表示不同级别的得分。 
 * - `children`: 包含子级结果的列表，类型为 `EvalSystemResultRsp` 列表。 
 * ### 注解 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述信息。 
 * ### 总结 
 * 这个类主要用于封装评估系统的各种结果数据，并且支持 JSON 序列化和反序列化。它具有递归结构，可以包含子级结果，适用于表示多层次的评估结果。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估系统结果响应
 *
 * @author SN
 * @date 2024/11/08
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EvalSystemResultRsp {

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
	 * exe 循环
	 */
	@ApiModelProperty(value = "执行轮次")
	private Integer exeCycle;

	/**
	 * 第一分
	 */
	@ApiModelProperty(value = "一级得分")
	private Integer firstScore;

	/**
	 * Second Score 第二分
	 */
	@ApiModelProperty(value = "二级得分")
	private Integer secondScore;

	/**
	 * 第三分
	 */
	@ApiModelProperty(value = "三级得分")
	private Integer thirdScore;

	/**
	 * 第四分
	 */
	@ApiModelProperty(value = "四级得分")
	private Integer fourthScore;

	/**
	 * 第五分
	 */
	@ApiModelProperty(value = "五级得分")
	private Integer fifthScore;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemResultRsp> children;

}