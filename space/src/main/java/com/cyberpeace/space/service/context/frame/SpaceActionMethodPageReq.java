package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动方法分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FrameBasePageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceActionMethodPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private List<String> targetId;

	public LambdaQueryWrapper<SpaceActionMethod> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionMethod> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionMethod::getName, this.getPattern());
		}

		return wrapper;
	}

	@Override
	public List<String> getUpperIds() {
		return this.targetId;
	}

}