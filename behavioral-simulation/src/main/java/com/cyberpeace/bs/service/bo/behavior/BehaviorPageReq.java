package com.cyberpeace.bs.service.bo.behavior;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class BehaviorPageReq extends PageReqBase {

	@ApiModelProperty(value = "设备类型")
	@Getter
	private Integer accessOs;

	@ApiModelProperty(value = "网络类型")
	@Getter
	private Integer networkType;
}
