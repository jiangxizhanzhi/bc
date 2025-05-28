/**
 * 这段代码定义了一个名为 `EvalSystemTreeRsp` 的 Java 类，用于表示评估系统的树形结构响应。以下是详细的分析： 
 * ### 类名及包名 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **类名**: `EvalSystemTreeRsp` 
 * - 类名 `EvalSystemTreeRsp` 表示这是一个与评估系统树相关的响应对象。 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter`: 自动生成所有属性的 getter 方法。 
 * - `@Setter`: 自动生成所有属性的 setter 方法。 
 * - **Swagger 注解** 
 * - `@ApiModelProperty`: 用于描述 Swagger 文档中的模型属性。 
 * - `value`: 属性的描述信息。 
 * ### 属性 
 * - **name** (String) 
 * - 描述: 名称 
 * - 注释: 该属性表示节点的名称。 
 * - **resourceId** (String) 
 * - 描述: 资源 ID 
 * - 注释: 该属性表示节点对应的资源 ID。 
 * - **level** (Integer) 
 * - 描述: 级别 
 * - 注释: 该属性表示节点在树中的层级。 
 * - **type** (Integer) 
 * - 描述: 3级指标项评分类型 
 * - 注释: 该属性表示节点的评分类型。 
 * - **thirdLevelType** (Integer) 
 * - 描述: 第三级类型 
 * - 注释: 该属性表示第三级指标项的评分选项。 
 * - **children** (List<EvalSystemTreeRsp>) 
 * - 描述: 子级列表 
 * - 注释: 该属性表示当前节点的子节点列表。 
 * ### 总结 
 * - **功能**: 这个类主要用于表示评估系统的树形结构数据。 
 * - **特性**: 使用 Lombok 注解简化了 getter 和 setter 方法的生成，使用 Swagger 注解来描述 API 文档中的模型属性。 
 * - **结构**: 类包含多个属性，其中 `children` 属性是一个递归引用，表示树形结构的层次关系。 
 * 这个类非常适合用于前端展示树形结构的数据，并且可以通过 Swagger 文档方便地进行 API 文档的生成和维护。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估系统树响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalSystemTreeRsp {

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
	 * 第三级类型
	 */
	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemTreeRsp> children;

}