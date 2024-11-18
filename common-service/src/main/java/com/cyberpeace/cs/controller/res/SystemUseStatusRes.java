package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用状态结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SystemUseStatusRes implements Serializable {

	/**
	 * XAXIS 公司
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("x轴，时间")
	private List<String> xaxis;

	/**
	 * 中央数据
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("cpu")
	private List<String> cpudata;

	/**
	 * 磁盘数据
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("disk")
	private List<String> diskdata;

	/**
	 * RAMDATA
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("memory")
	private List<String> ramdata;

}
