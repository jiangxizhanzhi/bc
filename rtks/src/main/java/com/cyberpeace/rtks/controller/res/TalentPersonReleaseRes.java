package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TalentPersonReleaseRes implements Serializable {

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("优秀")
	private Integer excellent;

	@ApiModelProperty("合格")
	private Integer qualified;

	@ApiModelProperty("其他")
	private Integer other;
}