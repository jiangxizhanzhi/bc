package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.req.FrameBasePageReq;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceActionFramePageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private Integer type;

	@Getter
	@Setter
	private Integer role;

	@Getter
	@Setter
	private String category_id;
	public LambdaQueryWrapper<SpaceActionFrame> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		if (null != this.type) {
			wrapper.eq(SpaceActionFrame::getType, this.type);
		}

		if (null != this.role) {
			wrapper.eq(SpaceActionFrame::getRole, this.role);
		}
		if (null != this.category_id) {
			wrapper.eq(SpaceActionFrame::getCategoryId, this.category_id);
		}
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionFrame::getName, this.getPattern());
		}
		wrapper.setEntityClass(SpaceActionFrame.class);
		return wrapper;
	}

}