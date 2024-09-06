package com.cyberpeace.oj.ojbase.service.bo.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostModifyReq extends PostAddReq {

	@ApiModelProperty(value = "id")
	private String resourceId;
}
