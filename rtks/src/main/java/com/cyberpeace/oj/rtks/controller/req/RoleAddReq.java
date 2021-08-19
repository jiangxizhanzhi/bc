package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class RoleAddReq implements Serializable {

	private String resourceId;

	@NotBlank
	private String name;

	private String description;

	@NotBlank
	private String levelId;

	@NotNull
	private int category;

}
