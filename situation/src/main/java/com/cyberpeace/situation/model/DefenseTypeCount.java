package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DefenseTypeCount implements Serializable {

	@ApiModelProperty("防御类型")
	private String defenseType;

	@ApiModelProperty("防御次数")
	private Integer times;
}
