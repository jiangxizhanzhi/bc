package com.cyberpeace.evaluate.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ValidationInstanceAddReq implements Serializable {

	public void validate() {

	}

	@ApiModelProperty("评估id")
	private String testInfoId;
}
