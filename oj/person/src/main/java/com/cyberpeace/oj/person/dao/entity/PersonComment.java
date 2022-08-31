package com.cyberpeace.oj.person.dao.entity;

import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PersonComment extends CommentReq {

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("上层资源编号")
	private String rootBusinessId;

	@ApiModelProperty("用户编号")
	private String userId;
}
