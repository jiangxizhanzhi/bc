package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel
public class SpaceActionPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	@ApiModelProperty("类型，1攻击2防御")
	private Integer type;

	/**
	 * 方法id
	 */
	@Getter
	@Setter
	@ApiModelProperty(value = "方法id")
	private List<String> methodId;

	public LambdaQueryWrapper<SpaceAction> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceAction> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceAction::getName, this.getPattern());
		}
		if(null != this.type){
			wrapper.eq(SpaceAction::getType, this.type);
			wrapper.setEntityClass(SpaceAction.class);
		}
		return wrapper;
	}

	public List<String> getUpperIds() {
		return this.methodId;
	}
}