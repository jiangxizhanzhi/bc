package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionDefense;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceActionDefenseBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

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
	private String createTime;

	public SpaceActionDefense bo2Entity() {
		SpaceActionDefense entity = BeanUtils.copy(this, SpaceActionDefense.class);
		entity.setMethod(this.method.getResourceId());
		entity.setTool(JsonUtil.arrayToString(this.tool));
		entity.setStrategy(JsonUtil.arrayToString(this.strategy));
		return entity;
	}

}
