/**
 * ### 分析与总结 
 * #### 代码概述： 
 * 该代码定义了一个名为 `SpaceAlgorithmRoleBo` 的 Java 类，它是 `SpaceBoBase` 的子类。该类主要用于表示空间算法岗位的相关业务对象（BO），并提供了从 BO 转换为实体对象的方法。 
 * --- 
 * #### 主要功能： 
 * 1. **属性定义**： 
 * - `resourceId`: 资源 ID。 
 * - `name`: 岗位名称。 
 * - `course`: 课程列表，使用 `List<IdAndName>` 表示。 
 * - `coursePercent`: 课程占比。 
 * - `training`: 训练任务列表，使用 `List<IdAndName>` 表示。 
 * - `trainingPercent`: 训练任务占比。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述 API 文档中字段的含义。 
 * 3. **方法实现**： 
 * - `bo2Entity()`: 将 `SpaceAlgorithmRoleBo` 对象转换为 `SpaceAlgorithmRole` 实体对象。通过 `BeanUtils.copy()` 方法将 BO 属性复制到实体对象，并调用 `getIds()` 方法处理 `course` 和 `training` 列表，将其转换为对应的 ID 列表。 
 * 4. **依赖项**： 
 * - `BeanUtils.copy()`: 提供对象属性复制的功能。 
 * - `IdAndName`: 一个简单的 DTO 类，通常包含 `id` 和 `name` 属性。 
 * - `SpaceAlgorithmRole`: 数据库实体类。 
 * - `SpaceBoBase`: 父类，可能包含一些通用属性或方法。 
 * --- 
 * #### 特点： 
 * - 该类是一个典型的 BO（Business Object）类，主要用于数据传输和业务逻辑封装。 
 * - 提供了从 BO 到实体对象的转换方法，便于在服务层和持久层之间进行数据交互。 
 * - 使用了 Lombok 和 Swagger 注解，减少了冗余代码，提高了开发效率。 
 * --- 
 * #### 可能的改进建议： 
 * 1. **异常处理**：在 `bo2Entity()` 方法中未处理潜在的异常（如空指针异常或类型转换错误）。可以添加必要的检查和异常捕获。 
 * 2. **泛型支持**：如果 `getIds()` 方法是通用的，可以考虑将其抽象为工具方法，避免重复代码。 
 * 3. **文档完善**：增加更多注释，特别是对复杂逻辑部分的解释。 
 * --- 
 * #### 总结： 
 * 该类设计清晰，功能明确，符合面向对象的设计原则。通过 Lombok 和 Swagger 的使用，简化了代码编写和维护工作。同时，提供了一种便捷的 BO 到实体对象的转换方式，适用于业务场景中的数据流转需求。 
**/

package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmRole;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间算法岗位Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmRoleBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 课程
	 */
	@ApiModelProperty(value = "课程列表")
	private List<IdAndName> course;
	/**
	 * 百分比
	 */
	@ApiModelProperty(value = "课程占比")
	private Integer coursePercent;
	/**
	 * 训练
	 */
	@ApiModelProperty(value = "训练列表")
	private List<IdAndName> training;

	/**
	 * 训练百分之
	 */
	@ApiModelProperty(value = "训练占比")
	private Integer trainingPercent;

	public SpaceAlgorithmRole bo2Entity() {
		SpaceAlgorithmRole entity = BeanUtils.copy(this, SpaceAlgorithmRole.class);
		entity.setCourse(this.getIds(this.course));
		entity.setTraining(this.getIds(this.training));
		return entity;
	}

}
    