package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionDefense;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;

@ApiModel
public class SpaceActionDefensePageReq extends FrameBasePageReq {

	public LambdaQueryWrapper<SpaceActionDefense> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionDefense> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionDefense::getName, this.getPattern());
		}
		return wrapper;
	}

}