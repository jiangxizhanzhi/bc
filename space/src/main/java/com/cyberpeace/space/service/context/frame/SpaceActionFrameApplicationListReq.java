package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel
public class SpaceActionFrameApplicationListReq extends SpacePageReqBase {

	@ApiModelProperty(value = "红蓝对抗id")
	@Getter
	@Setter
    @NotNull
	private String rvb_id;

	@ApiModelProperty(value = "面向流程1，面向目标2")
	@Getter
	@Setter
	private Integer type;

	@Override
	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SpaceActionFrameApplication::getId);
		wrapper.eq(SpaceActionFrameApplication::getRvbId, this.getRvb_id());
		if (null != this.getType()) {
			wrapper.eq(SpaceActionFrameApplication::getType, this.getType());
		}
		wrapper.setEntityClass(SpaceActionFrameApplication.class);
		return wrapper;
	}
}
