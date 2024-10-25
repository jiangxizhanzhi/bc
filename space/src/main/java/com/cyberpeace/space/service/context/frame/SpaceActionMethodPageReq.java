package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;

import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceActionMethodPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private String targetId;

	public LambdaQueryWrapper<SpaceActionMethod> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionMethod> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionMethod::getName, this.getPattern());
		}

		return wrapper;
	}

	@Override
	public String getUpperId() {
		return this.targetId;
	}

}