package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 当前时间响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CurrentTimeRsp {

	/**
	 * 当前时间
	 */
	@ApiModelProperty(value = "当前时间")
	private Date currentTime;
}
