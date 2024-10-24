package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceActionPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	@ApiModelProperty("类型，1攻击2防御")
	private Integer type;

	public LambdaQueryWrapper<SpaceAction> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceAction> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceAction::getName, this.getPattern());
		}
		if(null != this.type){
			wrapper.eq(SpaceAction::getType, this.type);
		}
		return wrapper;
	}

}