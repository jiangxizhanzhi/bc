package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceActionFrameOrderListReq extends SpacePageReqBase {

	@ApiModelProperty(value = "红蓝对抗id，必传")
	@Getter
	@Setter
	private String rvb_id;

	@ApiModelProperty(value = "当前用户id,必传")
	@Getter
	@Setter
	private String user_id;

	@ApiModelProperty(value = "完成状态，完成1，未完成2")
	@Getter
	@Setter
	private Integer status;

	@Override
	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SpaceActionFrameOrder::getId);
		if (null != this.status) {
			wrapper.eq(SpaceActionFrameOrder::getStatus, this.status);
		}
		if (StringUtils.isNotBlank(this.rvb_id)) {
			wrapper.eq(SpaceActionFrameOrder::getRvbId, this.rvb_id);
		}
		wrapper.eq(SpaceActionFrameOrder::getUserId, this.user_id);
		wrapper.setEntityClass(SpaceActionFrameOrder.class);
		return wrapper;
	}
}
