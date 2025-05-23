package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceLoadedFileThreatRulePageReq extends SpaceToolPageReq {

	@Getter
	@Setter
	private String type;

	@Override
	public LambdaQueryWrapper<SpaceLoadedFileThreatRule> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceLoadedFileThreatRule::getName, this.getPattern());
		}
		if (StringUtils.isNotBlank(this.getTool())) {
			wrapper.like(SpaceLoadedFileThreatRule::getTool, this.getTool());
		}

		if (StringUtils.isNotBlank(this.getType())) {
			wrapper.like(SpaceLoadedFileThreatRule::getType, this.getType());
		}
		if (null != this.getStage()) {
			wrapper.eq(SpaceLoadedFileThreatRule::getStage, this.getStage());
		}

		wrapper.orderByAsc(
				SpaceLoadedFileThreatRule::getUpdateTime);

		wrapper.setEntityClass(SpaceLoadedFileThreatRule.class);
		return wrapper;
	}
}
