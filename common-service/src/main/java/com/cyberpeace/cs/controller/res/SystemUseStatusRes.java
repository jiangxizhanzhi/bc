package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
