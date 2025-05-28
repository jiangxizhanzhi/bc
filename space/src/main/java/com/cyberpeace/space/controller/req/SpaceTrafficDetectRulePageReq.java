package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

/**
 * 空间流量检测统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceTrafficDetectRulePageReq extends SpaceToolPageReq {

	@Override
	public LambdaQueryWrapper<SpaceTrafficDetectRule> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceTrafficDetectRule> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceTrafficDetectRule::getUpdateTime);

		if (StringUtils.isNotBlank(this.getTool())) {
			wrapper.like(SpaceTrafficDetectRule::getTool, this.getTool());
		}
		if (null != this.getStage()) {
			wrapper.eq(SpaceTrafficDetectRule::getStage, this.getStage());
		}
		wrapper.setEntityClass(SpaceTrafficDetectRule.class);
		return wrapper;
	}
}
