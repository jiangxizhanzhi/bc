package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class OperationUpdateReq extends OperationAddReq {

	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	@ApiModelProperty(name = "file_url", value = "附件路径")
	private String fileUrl;

	public void setFile_url(String filePath) {
		this.fileUrl = filePath;
	}

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}
}
