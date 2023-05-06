package com.cyberpeace.cs.controller.res;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统状态res
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SystemUseStatusRes implements Serializable {

	@ApiModelProperty("x轴，时间")
	private List<String> xaxis;

	@ApiModelProperty("cpu")
	private List<String> cpudata;

	@ApiModelProperty("disk")
	private List<String> diskdata;

	@ApiModelProperty("memory")
	private List<String> ramdata;

}
