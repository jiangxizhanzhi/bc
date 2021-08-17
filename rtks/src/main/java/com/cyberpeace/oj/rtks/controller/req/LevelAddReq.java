package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class LevelAddReq implements Serializable {

	@NotBlank
	private String name;

	private String description;

	private String parent;
}
