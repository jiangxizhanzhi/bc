package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动框架Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameBo` 的类，继承自 `SpaceFrameBoBase`，用于表示空间行动框架的业务对象（BO）。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `SpaceActionFrameBo` 是一个数据传输对象（DTO），用于封装与空间行动框架相关的业务逻辑。 
 * - 它包含了多个字段，如 `resourceId`, `name`, `type`, `categoryId`, `role`, `attack`, `defense`, `tag`, `topo`, `description`, 和 `analyze`，这些字段通过 `@ApiModelProperty` 注解描述了其用途。 
 * 2. **继承关系**： 
 * - 该类继承自 `SpaceFrameBoBase`，表明它可能扩展或实现了某些通用功能。 
 * 3. **方法**： 
 * - `bo2Entity()` 方法将当前对象转换为实体对象 `SpaceActionFrame`。在转换过程中，使用了工具类 `BeanUtils` 进行属性复制，并通过 `JsonUtil.arrayToString(this.tag)` 将列表类型的 `tag` 转换为字符串形式。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法。 
 * - Swagger 注解 `@ApiModelProperty` 用于描述 API 接口中的参数和响应内容。 
 * 5. **用途**： 
 * - 该类主要用于在业务层和数据库层之间传递数据，特别是在从业务对象到实体对象的映射过程中发挥作用。 
 * 总结来说，这段代码是一个典型的业务对象类，提供了数据封装、转换以及与外部接口交互的功能。 
**/
@Getter
@Setter
public class SpaceActionFrameBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "分类，面向流程，面向目标")
	private Integer type;

	@ApiModelProperty(value = "类型id")
	private String categoryId;

	@ApiModelProperty(value = "岗位")
	private Integer role;

	@ApiModelProperty(value = "攻击类型")
	private String attack;

	@ApiModelProperty(value = "防御类型")
	private String defense;

	@ApiModelProperty(value = "应用场景")
	private List<String> tag;

	@ApiModelProperty(value = "拓扑特征")
	private String topo;

	@ApiModelProperty(value = "使用说明")
	private String description;

	@ApiModelProperty(value = "攻防动作解析")
	private String analyze;

	@ApiModelProperty(value = "是否内置")
	private Boolean builtin;

	public SpaceActionFrame bo2Entity() {
		SpaceActionFrame entity = BeanUtils.copy(this, SpaceActionFrame.class);
		entity.setTag(JsonUtil.arrayToString(this.tag));
		return entity;
	}

}

