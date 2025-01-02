package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceAptThreatDetectRulePageReq extends SpacePageReqBase {

	@Getter
	@Setter
	@ApiModelProperty(value = "APT代号")
	private String apt_code;

	@Getter
	@Setter
	@ApiModelProperty(value = "工具名称")
	private String tool;

	@Override
	public LambdaQueryWrapper<SpaceAptThreatDetectRule> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceAptThreatDetectRule> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceAptThreatDetectRule::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceAptThreatDetectRule::getName, this.getPattern());
		}
		if (StringUtils.isNotBlank(this.getApt_code())) {
			wrapper.like(SpaceAptThreatDetectRule::getAptCode, this.getApt_code());
		}
		if (StringUtils.isNotBlank(this.getTool())) {
			wrapper.like(SpaceAptThreatDetectRule::getTool, this.getTool());
		}
		wrapper.setEntityClass(SpaceAptThreatDetectRule.class);
		return wrapper;
	}
}
