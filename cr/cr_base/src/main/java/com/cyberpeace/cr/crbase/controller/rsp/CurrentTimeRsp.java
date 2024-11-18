package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 当前时间响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class CurrentTimeRsp {

	/**
	 * 当前时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "当前时间")
	private Date currentTime;
}
