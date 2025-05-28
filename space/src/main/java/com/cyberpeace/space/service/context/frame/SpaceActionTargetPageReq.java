package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;

import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动目标分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FrameBasePageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceActionTargetPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private String frameId;

	public LambdaQueryWrapper<SpaceActionTarget> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionTarget> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionTarget::getName, this.getPattern());
		}
		return wrapper;
	}

	@Override
	public String getUpperId() {
		return this.frameId;
	}

}