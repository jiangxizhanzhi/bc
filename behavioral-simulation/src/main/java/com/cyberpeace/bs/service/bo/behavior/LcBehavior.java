package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LcBehavior extends Behavior implements Serializable {

	@ApiModelProperty(value = "行为应用")
	private String application;

}
