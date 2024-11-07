package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具壳代号列表请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class ToolShellCodeListReq extends PageReqBase {

	/**
	 * 平台
	 */
	@ApiModelProperty(value = "平台")
	private String platform;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	private String author;

	/**
	 * EDB 验证
	 */
	@ApiModelProperty(name = "edb_verified", value = "是否验证")
	private Boolean edbVerified;

	public void setEdb_verified(Boolean edbVerified) {
		this.edbVerified = edbVerified;
	}
}
