package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用状态结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SystemUseStatusRes implements Serializable {

	/**
	 * XAXIS 公司
	 */
	@ApiModelProperty("x轴，时间")
	private List<String> xaxis;

	/**
	 * 中央数据
	 */
	@ApiModelProperty("cpu")
	private List<String> cpudata;

	/**
	 * 磁盘数据
	 */
	@ApiModelProperty("disk")
	private List<String> diskdata;

	/**
	 * RAMDATA
	 */
	@ApiModelProperty("memory")
	private List<String> ramdata;

}
