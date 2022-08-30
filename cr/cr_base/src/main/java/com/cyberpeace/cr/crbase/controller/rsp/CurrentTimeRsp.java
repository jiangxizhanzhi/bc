package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CurrentTimeRsp {

	@ApiModelProperty(value = "当前时间")
	private Date currentTime;
}
