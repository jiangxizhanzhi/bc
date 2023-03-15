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

	@ApiModelProperty(value = "客户端类型，type=45")
	@Getter
	@Setter
	private Integer appType;

	@ApiModelProperty(value = "是否实体设备")
	@Getter
	@Setter
	private Integer isActual;

}
