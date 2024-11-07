package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源id请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class ResourceIdReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

}