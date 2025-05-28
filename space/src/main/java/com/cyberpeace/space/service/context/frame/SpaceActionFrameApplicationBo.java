/**
 * 这段代码定义了一个名为 `SpaceActionFrameApplicationBo` 的 Java 类，用于表示空间行动框架的行为依赖对象。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 这个类继承自 `SpaceBoBase`，并扩展了空间行动框架的相关功能。 
 * - 它主要用于将业务逻辑中的数据模型转换为实体对象。 
 * 2. **注解与字段**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供描述信息，便于 API 文档生成。 
 * - 字段包括 `resourceId`, `rvbName`, `rvbId`, `frameId`, `name`, `type`, `categoryId`, `tag`, `description`, `analyze`, 和 `frame`。 
 * 3. **方法**： 
 * - `bo2Entity()` 方法将当前对象 (`this`) 转换为 `SpaceActionFrameApplication` 实体对象。 
 * - 使用 `BeanUtils.copy()` 方法进行浅拷贝，并通过 `JsonUtil` 将 `tag` 列表和 `frame` 对象序列化为字符串。 
 * 4. **依赖**： 
 * - 依赖于 `BeanUtils`、`JsonUtil`、`SpaceActionFrameApplication` 和 `SpaceBoBase`。 
 * - 使用了 Lombok 和 Swagger 注解库。 
 * 总结：该类的主要目的是在业务逻辑层和持久化层之间进行数据转换，支持从业务对象到实体对象的映射操作。 
**/

package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动框架行为依赖Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameApplicationBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "红蓝对抗业务名称")
	private String rvbName;

	@ApiModelProperty(value = "红蓝对抗业务id")
	private String rvbId;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "框架名称")
	private String name;

	@ApiModelProperty(value = "分类，面向流程，面向目标")
	private Integer type;

	@ApiModelProperty(value = "类型id")
	private String categoryId;

	@ApiModelProperty(value = "应用场景")
	private List<String> tag;

	@ApiModelProperty(value = "使用说明")
	private String description;

	@ApiModelProperty(value = "攻防动作解析")
	private String analyze;

	@ApiModelProperty(value = "框架编排")
	private ActionFrameArrangeBo frame;

	public SpaceActionFrameApplication bo2Entity() {
		SpaceActionFrameApplication entity = BeanUtils.copy(this,
				SpaceActionFrameApplication.class);
		entity.setTag(JsonUtil.arrayToString(this.tag));
		entity.setFrame(JsonUtil.toJsonString(this.frame));
		return entity;
	}

}
