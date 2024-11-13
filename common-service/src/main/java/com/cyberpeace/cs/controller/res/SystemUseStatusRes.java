package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用状态结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SystemUseStatusRes implements Serializable {

	/**
	 * XAXIS
	 */
	@ApiModelProperty("x轴，时间")
	private List<String> xaxis;

	/**
	 * cpu数据
	 */
	@ApiModelProperty("cpu")
	private List<String> cpudata;

	/**
	 * 磁盘数据
	 */
	@ApiModelProperty("disk")
	private List<String> diskdata;

	/**
	 * 内存DATA
	 */
	@ApiModelProperty("memory")
	private List<String> ramdata;

}
