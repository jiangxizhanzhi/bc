package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ToolShellCodeListReq extends PageReqBase {

	@ApiModelProperty(value = "平台")
	private String platform;

	@ApiModelProperty(value = "作者")
	private String author;

	@ApiModelProperty(name = "edb_verified", value = "是否验证")
	private Boolean edbVerified;

	public void setEdb_verified(Boolean edbVerified) {
		this.edbVerified = edbVerified;
	}
}
