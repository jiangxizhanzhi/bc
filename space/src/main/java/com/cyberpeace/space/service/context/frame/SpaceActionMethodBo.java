package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 空间行动方法Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionMethodBo` 的 Java 类，它是 `SpaceFrameBoBase` 的子类。以下是代码的主要功能和结构分析： 
 * 1. **类的继承与注解**： 
 * - `SpaceActionMethodBo` 继承自 `SpaceFrameBoBase`。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * 2. **字段及注解**： 
 * - 包含多个字段，每个字段都有 `@ApiModelProperty` 注解，用于描述字段的用途。例如： 
 * - `resourceId`: 资源ID。 
 * - `name`: 名称。 
 * - `description`: 描述。 
 * - `tag`: 标签列表。 
 * - `createTime`: 创建时间，标记为前端不可传。 
 * 3. **方法**： 
 * - `bo2Entity()`: 将当前对象转换为 `SpaceActionMethod` 实体对象。使用了 `BeanUtils.copy()` 方法复制字段，并通过 `JsonUtil.arrayToString()` 将 `tag` 列表转换为字符串。 
 * 4. **依赖**： 
 * - 使用了 `com.cyberpeace.common.util.BeanUtils` 和 `com.cyberpeace.common.util.JsonUtil` 工具类来辅助字段的复制和转换。 
 * 总结：该类主要用于将业务对象（BO）转换为实体对象（Entity），并提供了字段的自动管理功能。 
**/
@Getter
@Setter
public class SpaceActionMethodBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	// @ApiModelProperty(value = "所属作战目标")
	// private List<IdAndName> target;
	@ApiModelProperty(value = "创建时间，前端不要传")
	private Date createTime;

	public SpaceActionMethod bo2Entity() {
		SpaceActionMethod entity = BeanUtils.copy(this, SpaceActionMethod.class);
		entity.setTag(JsonUtil.arrayToString(this.tag));
		return entity;
	}

}

