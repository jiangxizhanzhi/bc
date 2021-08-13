package com.cyberpeace.oj.demo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class InputDemoModel implements Serializable {

	@ApiModelProperty(name = "resource_id", value = "资源id", required = true)
	@NotBlank
	private String resourceId;

	// @Size(min = 3, max = 100)
	@Setter
	@Getter
	private String name;

	public String getResourceId() {
		return resourceId;
	}

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}
}
