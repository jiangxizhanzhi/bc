package com.cyberpeace.oj.person.dao.entity;

import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PersonComment extends CommentReq {

	@ApiModelProperty("评论资源编号")
	private String resourceId;

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("上层资源编号")
	private String rootBusinessId;

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("创建时间")
	private String createTime;

	@ApiModelProperty("评论回复数量")
	private Integer replyNum;

	@ApiModelProperty("被评论名称")
	private String rootComment;
}
