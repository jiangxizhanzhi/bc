package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneOperationReq implements Serializable {

	@ApiModelProperty("课时、习题编号")
	@NotBlank
	private String resourceId;

}
