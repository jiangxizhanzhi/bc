/**
 * ### 代码分析 
 * #### 概述 
 * 该代码定义了一个名为 `MissionContext` 的类，继承自 `Mission` 类。这是一个用于任务上下文的模型类，扩展了基础任务实体的功能，并引入了一些额外的属性和注解。 
 * --- 
 * #### 主要特点 
 * 1. **继承关系** 
 * - `MissionContext` 继承自 `Mission`，表明它是一个基于现有任务实体的增强版本。 
 * - 这种设计通常用于在保留原有功能的基础上添加新的字段或逻辑。 
 * 2. **属性说明** 
 * - `state`: 表示任务所在的州（可能是状态码）。 
 * - `stateString`: 将 `state` 转换为可读的状态描述字符串。 
 * - `trainingRecord`: 关联的训练记录对象，可能用于记录任务相关的训练信息。 
 * - `finished`: 是否已完成任务的标志，通过 Swagger 注解 `@ApiModelProperty` 描述其用途。 
 * 3. **注解** 
 * - `@Getter` 和 `@Setter`: Lombok 提供的注解，自动生成 getter 和 setter 方法，简化代码。 
 * - `@ApiModelProperty`: Swagger 注解，用于生成 API 文档时标注字段的含义。 
 * 4. **设计目的** 
 * - 扩展了原始任务实体的功能，增加了与业务逻辑相关的字段（如 `stateString` 和 `finished`），使得 `MissionContext` 更适合复杂的业务场景。 
 * - 引入了关联对象（如 `TrainingRecord`），便于管理任务与训练记录之间的关系。 
 * --- 
 * #### 总结 
 * `MissionContext` 是一个典型的领域模型类，继承自 `Mission` 并增强了功能。它结合了 Swagger 注解和 Lombok 工具，提升了开发效率并方便文档生成。整体结构清晰，适合用作任务管理系统的上下文模型。 
**/
package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionContext extends Mission {

	/**
	 * 州
	 */
	private Integer state;
	/**
	 * 状态字符串
	 */
	private String stateString;

	/**
	 * 训练记录
	 */
	private TrainingRecord trainingRecord;

	/**
	 * 完成
	 */
	@ApiModelProperty("单兵训练使用, 标识此任务是否已提交")
	private Boolean finished;

}
