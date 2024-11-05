package com.cyberpeace.space.model.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;

@ApiModel
public class FrameBasePageReq extends PageReqBase {

	public LambdaQueryWrapper req2QueryWrapper() {
		return null;
	}

	public String getUpperId() {
		return null;
	}
}
