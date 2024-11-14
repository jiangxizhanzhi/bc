package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源id请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ResourceIdReq implements Serializable {

	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

}