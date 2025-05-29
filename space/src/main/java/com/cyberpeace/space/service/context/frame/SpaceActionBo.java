package com.cyberpeace.space.service.context.frame;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBoBase
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceActionBo` 的类，继承自 `SpaceFrameBoBase`。以下是代码的主要分析和总结： 
 * ### 类的功能 
 * - **类名**：`SpaceActionBo`，表示空间行动的业务对象。 
 * - **继承**：继承自 `SpaceFrameBoBase`，可能是一个通用的基础类。 
 * - **注解**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性说明 
 * - `resourceId`：资源 ID。 
 * - `name`：名称。 
 * - `type`：类型，1 表示攻击，2 表示防御。 
 * - `idNumber`：ID 编号。 
 * - `parentNumber`：父活动编号。 
 * - `platform`：支持的平台列表。 
 * - `description`：描述信息。 
 * - `method`：所属交战方法列表。 
 * - `environment`：工作原理。 
 * - `action`：具体动作。 
 * - `analyze`：注意事项。 
 * - `tool`：在线工具列表。 
 * - `offlineTool`：离线工具列表。 
 * - `strategy`：相关策略列表。 
 * - `createTime`：创建时间（仅用于后端生成）。 
 * ### 方法说明 
 * - `bo2Entity()`：将 `SpaceActionBo` 对象转换为 `SpaceAction` 实体对象。 
 * - 使用 `BeanUtils.copy` 进行属性复制。 
 * - 将 `tool`、`strategy`、`platform` 和 `offlineTool` 转换为 JSON 字符串。 
 * ### 总结 
 * 该类主要用于封装与空间行动相关的业务数据，并提供从业务对象到实体对象的转换功能。通过 Lombok 和 Stream API 提高了代码的简洁性和可读性。 
**/
@Getter
@Setter
public class SpaceActionBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类型，1：攻击，2：防御")
	private Integer type;

	@ApiModelProperty(value = "ID编号")
	private String idNumber;

	@ApiModelProperty(value = "父活动编号")
	private String parentNumber;

	@ApiModelProperty(value = "平台")
	private List<String> platform;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "所属交战方法")
	private List<IdAndName> method;

	@ApiModelProperty(value = "工作原理")
	private String environment;

	@ApiModelProperty(value = "动作")
	private String action;

	@ApiModelProperty(value = "注意事项")
	private String analyze;

	@ApiModelProperty(value = "在线工具")
	private List<SpaceActionTool> tool;

	@ApiModelProperty(value = "离线工具")
	private List<SpaceActionOfflineTool> offlineTool;

	@ApiModelProperty(value = "相关策略")
	private List<SpaceActionStrategy> strategy;

	@ApiModelProperty(value = "创建时间，前端不要传")
	private Date createTime;

	public SpaceAction bo2Entity() {
		SpaceAction entity = BeanUtils.copy(this, SpaceAction.class);
		entity.setTool(JsonUtil
				.arrayToString(StreamApiUtils.map(this.tool, SpaceActionTool::getResourceId)));
		entity.setStrategy(JsonUtil.arrayToString(
				StreamApiUtils.map(this.strategy, SpaceActionStrategy::getResourceId)));
		entity.setPlatform(JsonUtil.arrayToString(this.platform));
		entity.setOfflineTool(JsonUtil.arrayToString(this.offlineTool));
		return entity;
	}

}

