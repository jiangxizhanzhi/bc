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
 * @date 2024/11/11
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

	public void setResource_ids(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
}
