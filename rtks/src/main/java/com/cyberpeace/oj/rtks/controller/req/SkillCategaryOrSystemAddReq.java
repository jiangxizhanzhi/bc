package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class SkillCategaryOrSystemAddReq implements Serializable {

	@NotBlank
	private String content;
}
