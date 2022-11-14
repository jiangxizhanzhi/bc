package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SituationInstanceReleaseReq implements Serializable {

	@ApiModelProperty(value = "态势实例id", required = true)
	@NotBlank
	private String resourceId;
}
