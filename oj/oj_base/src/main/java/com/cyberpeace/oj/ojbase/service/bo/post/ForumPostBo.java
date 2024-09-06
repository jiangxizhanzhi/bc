package com.cyberpeace.oj.ojbase.service.bo.post;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForumPostBo {

	@ApiModelProperty(value = "id")
	private String resourceId;

	// @ApiModelProperty(value = "")
	// private Integer type;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "正文")
	private String content;

	@ApiModelProperty(value = "发布人id")
	private String userId;

	@ApiModelProperty(value = "发布人姓名")
	private String userName;

	@ApiModelProperty(value = "发布时间")
	private Date createTime;

	@ApiModelProperty(value = "是否可编辑")
	private Boolean editable;

}
