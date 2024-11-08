package com.cyberpeace.gateway.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 外部批量请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class ExternalBatchReq implements Serializable {

	/**
	 * 资源id列表
	 */
	@ApiModelProperty(name = "resource_ids", value = "资源编号数组")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 设置资源id列表
	 *
	 * @param resourceIds 资源id列表
	 */
	public void setResource_ids(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
}
