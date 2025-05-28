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

	public SpaceActionFrame bo2Entity() {
		SpaceActionFrame entity = BeanUtils.copy(this, SpaceActionFrame.class);
		entity.setTag(JsonUtil.arrayToString(this.tag));
		return entity;
	}

}
