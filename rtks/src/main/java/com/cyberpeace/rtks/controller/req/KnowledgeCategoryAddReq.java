package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class KnowledgeCategoryAddReq implements Serializable {

	@NotBlank
	private String content;
}
