package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 实操更新请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class OperationUpdateReq extends OperationAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}
}
