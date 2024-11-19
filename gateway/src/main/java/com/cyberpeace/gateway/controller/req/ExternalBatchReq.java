package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 外部批量请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ExternalBatchReq implements Serializable {

	/**
	 * 资源编号数组
	 */
	@ApiModelProperty(name = "resource_ids", value = "资源编号数组")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 设置资源id列表
	 *
	 * @param resourceIds 资源id列表
	 * @throws
	 * @see List<String>
	 */
	public void setResource_ids(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
}
