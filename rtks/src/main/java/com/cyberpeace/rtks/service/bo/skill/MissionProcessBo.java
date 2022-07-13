package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionProcessBo extends MissionTemplateSimple implements Serializable {

	@ApiModelProperty("完成度")
	private Integer process = 0;
}
