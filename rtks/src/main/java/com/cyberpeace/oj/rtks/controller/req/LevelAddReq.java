package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LevelAddReq implements Serializable {

	@ApiModelProperty()
	private String resourceId;

	@NotBlank
	private String name;

	private String description;

	@NotBlank
	private String parent;

}
