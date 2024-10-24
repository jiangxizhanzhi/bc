package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceActionFrameBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "分类")
	private Integer type;

	@ApiModelProperty(value = "岗位")
	private Integer role;

	@ApiModelProperty(value = "攻击类型")
	private List<String> attack;

	@ApiModelProperty(value = "防御类型")
	private List<String> defense;

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	@ApiModelProperty(value = "拓扑特征")
	private String topo;

	@ApiModelProperty(value = "介绍")
	private String description;

	public SpaceActionFrame bo2Entity() {
		SpaceActionFrame entity = BeanUtils.copy(this, SpaceActionFrame.class);
		entity.setAttack(JsonUtil.arrayToString(this.attack));
		entity.setDefense(JsonUtil.arrayToString(this.defense));
		entity.setTag(JsonUtil.arrayToString(this.tag));
		return entity;
	}

}
