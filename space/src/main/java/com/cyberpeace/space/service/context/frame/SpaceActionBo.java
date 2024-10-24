package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SpaceActionBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类型，1：攻击，2：防御")
	private Integer type;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "所属交战方法")
	private IdAndName method;

	@ApiModelProperty(value = "作战环境")
	private String environment;

	@ApiModelProperty(value = "动作")
	private String action;

	@ApiModelProperty(value = "分析")
	private String analyze;

	@ApiModelProperty(value = "相关工具")
	private List<SpaceActionTool> tool;

	@ApiModelProperty(value = "相关策略")
	private List<SpaceActionStrategy> strategy;

	@ApiModelProperty(value = "创建时间，前端不要传")
	private Date createTime;

	public SpaceAction bo2Entity() {
		SpaceAction entity = BeanUtils.copy(this, SpaceAction.class);
		entity.setMethod(this.method.getResourceId());
		entity.setTool(JsonUtil
				.arrayToString(StreamApiUtils.map(this.tool, SpaceActionTool::getResourceId)));
		entity.setStrategy(JsonUtil.arrayToString(
				StreamApiUtils.map(this.strategy, SpaceActionStrategy::getResourceId)));
		return entity;
	}

}
