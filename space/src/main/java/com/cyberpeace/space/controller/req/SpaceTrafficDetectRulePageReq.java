package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

@ApiModel
public class SpaceTrafficDetectRulePageReq extends SpaceToolPageReq {

	@Override
	public LambdaQueryWrapper<SpaceTrafficDetectRule> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceTrafficDetectRule> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceTrafficDetectRule::getUpdateTime);

		if (StringUtils.isNotBlank(this.getTool())) {
			wrapper.like(SpaceTrafficDetectRule::getTool, this.getTool());
		}
		wrapper.setEntityClass(SpaceTrafficDetectRule.class);
		return wrapper;
	}
}
