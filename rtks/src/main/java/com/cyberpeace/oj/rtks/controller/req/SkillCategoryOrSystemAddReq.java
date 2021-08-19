package com.cyberpeace.oj.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class SkillCategoryOrSystemAddReq implements Serializable {

	@NotBlank
	private String content;
}
