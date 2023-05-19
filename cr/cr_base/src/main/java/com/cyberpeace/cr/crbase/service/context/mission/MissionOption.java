package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionOption implements Serializable {

	@ApiModelProperty(value = "选项,字段名称保持optionLabel")
	@JsonProperty("optionLabel")
	private String optionLabel;

	@ApiModelProperty(value = "选项值,字段名称保持optionValue")
	@JsonProperty("optionValue")
	private String optionValue;
}
