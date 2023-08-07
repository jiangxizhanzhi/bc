package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class MissionFlagAttachmentQuery {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty(value = "flag值")
	private String flagValue;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件路径")
	private String filePath;

}