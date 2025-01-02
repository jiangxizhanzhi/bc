package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;

@ApiModel
public class SpaceLoadedFileThreatRulePageReq extends SpacePageReqBase {

	@Override
	public LambdaQueryWrapper<SpaceLoadedFileThreatRule> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceLoadedFileThreatRule::getName, this.getPattern());
		}
		wrapper.orderByDesc(SpaceLoadedFileThreatRule::getExecuteType,
				SpaceLoadedFileThreatRule::getUpdateTime);

		wrapper.setEntityClass(SpaceLoadedFileThreatRule.class);
		return wrapper;
	}
}
