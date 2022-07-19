package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionProcessBo extends MissionTemplateSimple implements Serializable {

	@ApiModelProperty("完成度")
	private Integer process = 0;

	@ApiModelProperty("0：未完成； 1：已完成")
	private Integer status = 0;
}
