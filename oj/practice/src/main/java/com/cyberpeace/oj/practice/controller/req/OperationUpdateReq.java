package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 实操更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see OperationAddReq
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class OperationUpdateReq extends OperationAddReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}
}
