package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 当前时间返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CurrentTimeRsp {

	@ApiModelProperty(value = "当前时间")
	private Date currentTime;
}
