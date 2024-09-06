package com.cyberpeace.oj.ojbase.service.bo.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostAddReq {

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "正文")
	private String content;
}
