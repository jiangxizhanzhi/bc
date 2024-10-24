package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionAttack;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;

@ApiModel
public class SpaceActionAttackPageReq extends FrameBasePageReq {

	public LambdaQueryWrapper<SpaceActionAttack> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionAttack> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionAttack::getName, this.getPattern());
		}
		return wrapper;
	}

}