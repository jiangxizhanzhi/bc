package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ToolShellCodeRsp {

	@ApiModelProperty(value = "作者")
	private String author;

	@ApiModelProperty(value = "平台")
	private String platform;

	@ApiModelProperty(name = "edb_verified", value = "是否验证")
	private Boolean edbVerified;
}
