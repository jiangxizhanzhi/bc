package com.cyberpeace.cr.crbase.service.context.wiki;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WikiListRsp implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "图标")
	private String logo;

	@ApiModelProperty(value = "创建者名称")
	private String creatorName;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
