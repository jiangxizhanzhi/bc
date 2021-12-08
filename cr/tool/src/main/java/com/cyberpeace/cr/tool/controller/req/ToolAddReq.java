package com.cyberpeace.cr.tool.controller.req;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ToolAddReq implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("分类")
	private String category;

	@ApiModelProperty("封面")
	private String cover;

	@ApiModelProperty("是否破解")
	private Boolean cracked;

	@ApiModelProperty("语言")
	private String language;

	@ApiModelProperty("平台")
	private String platforms;

	@ApiModelProperty("是否可见")
	private Boolean visible;

	@ApiModelProperty("版本")
	private String version;

	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("工具附件")
	private String files;

	@ApiModelProperty("工具类型")
	private Integer toolType;

	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	@ApiModelProperty("发布时间")
	private String publishTime;

	private Boolean toolPublic = true;
}
