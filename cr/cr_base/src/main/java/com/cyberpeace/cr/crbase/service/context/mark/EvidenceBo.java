package com.cyberpeace.cr.crbase.service.context.mark;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvidenceBo implements Serializable {

	@ApiModelProperty(value = "图片上传后返回的resource_id")
	private String resourceId;

	@ApiModelProperty(value = "文件名")
	private String filename;

	@ApiModelProperty(value = "文件路径")
	private String path;

}
