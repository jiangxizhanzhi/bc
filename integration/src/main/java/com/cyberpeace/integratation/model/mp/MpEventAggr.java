package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台事件aggr
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpEventAggr implements Serializable {

	/**
	 * 文件百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("文件百分比")
	private Double file;

	/**
	 * 网络连接百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("网络连接百分比")
	private Double network;

	/**
	 * 端口百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("端口百分比")
	private Double port;

	/**
	 * 进程百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("进程百分比")
	private Double process;

	/**
	 * 用户百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("用户百分比")
	private Double user;

}
