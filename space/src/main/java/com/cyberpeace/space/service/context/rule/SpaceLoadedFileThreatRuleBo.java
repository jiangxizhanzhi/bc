/**
 * ### 代码分析： 
 * #### 1. **类的功能** 
 * - `SpaceLoadedFileThreatRuleBo` 是一个基于 `SpaceBoBase` 的业务对象（BO），用于表示与“空间加载文件威胁规则”相关的数据模型。 
 * - 它主要用于封装与威胁规则相关的属性，并提供从 BO 到实体对象（`SpaceLoadedFileThreatRule`）的转换功能。 
 * #### 2. **类的主要属性** 
 * - `resourceId`: 资源 ID，标识特定资源。 
 * - `name`: 规则的名称。 
 * - `type`: 检测类型。 
 * - `description`: 内容简介。 
 * - `content`: 内容详情。 
 * - `tool`: 应用工具。 
 * - `executeType`: 执行方式，分为自动（值为 2）和手动（值为 1）。 
 * - `stage`: 防御阶段，与特定类型（如 `type=57`）相关联。 
 * #### 3. **方法** 
 * - `bo2Entity()`: 将当前 BO 对象 (`SpaceLoadedFileThreatRuleBo`) 转换为对应的实体对象 (`SpaceLoadedFileThreatRule`)。 
 * - 使用了 `BeanUtils.copy()` 方法，通过反射机制将 BO 的属性值复制到实体对象中。这是一种常见的对象映射方式。 
 * #### 4. **注解** 
 * - `@Getter` 和 `@Setter`: 自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`: 用于生成 API 文档，描述每个字段的意义。 
 * #### 5. **继承关系** 
 * - 继承自 `SpaceBoBase`，表明它可能共享一些基础功能或属性。 
 * #### 6. **设计特点** 
 * - **分层设计**: BO 层负责数据传输和业务逻辑的封装，而实体层（`SpaceLoadedFileThreatRule`）负责持久化操作。 
 * - **可扩展性**: 提供了从 BO 到实体的映射方法，便于在服务层和持久化层之间传递数据。 
 * #### 总结： 
 * 该类是一个典型的业务对象（BO），专注于封装“空间加载文件威胁规则”的数据，并提供了一种便捷的方式来将 BO 转换为实体对象。它的设计符合面向对象原则，具有良好的可读性和可维护性。 
**/

package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间加载文件威胁统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceLoadedFileThreatRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "应用工具")
	private String tool;

	/**
	 * 执行类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "执行方式, 自动2，手动1")
	private Integer executeType;

	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	/**
	 * BO2 实体
	 *
	 * @return 空间加载文件威胁规则
	 * @throws
	 * @see SpaceLoadedFileThreatRule
	 */
	public SpaceLoadedFileThreatRule bo2Entity() {
		SpaceLoadedFileThreatRule entity = BeanUtils.copy(this, SpaceLoadedFileThreatRule.class);
		return entity;
	}

}
    