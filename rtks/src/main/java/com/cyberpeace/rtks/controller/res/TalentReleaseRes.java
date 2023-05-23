package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TalentReleaseRes implements Serializable {

	@ApiModelProperty("岗位名称")
	private String appName;

	@ApiModelProperty("优秀")
	private Integer count;

	@ApiModelProperty("合格")
	private Integer count1;

	@ApiModelProperty("其他")
	private Integer count2;
}