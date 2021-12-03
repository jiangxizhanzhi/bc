package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@ApiModel
public class ResourceAttachmentReq implements Serializable {

	@ApiModelProperty(name = "resource_id", value = "资源编号")
	private String resourceId;

	@ApiModelProperty(name = "file", value = "附件内容")
	private MultipartFile file;

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}
}
