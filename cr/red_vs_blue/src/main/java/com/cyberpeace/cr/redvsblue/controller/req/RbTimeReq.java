package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 红蓝对抗时间请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTimeReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true, value = "资源id")
	@NotNull
	private String roomId;

	/**
	 * 速率
	 */
	@ApiModelProperty(required = true, value = "速率")
	@NotNull
	private Integer rate;

	/**
	 * 天文时间
	 */
	@ApiModelProperty(required = true, value = "天文时间")
	@NotNull
	private Date astronomicalTime;

	/**
	 * 作战时间
	 */
	@ApiModelProperty(required = true, value = "作战时间")
	@NotNull
	private String combatTime;

}