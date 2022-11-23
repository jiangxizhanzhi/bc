package com.cyberpeace.bs.service.bo.behavior;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class BehaviorPageReq extends PageReqBase {

	@ApiModelProperty(value = "设备类型")
	@Getter
	@Setter
	private Integer accessOs;

	@ApiModelProperty(value = "网络类型")
	@Getter
	@Setter
	private Integer networkType;
}
