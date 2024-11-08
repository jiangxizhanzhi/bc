package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源公共请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ResourcePublicReq implements Serializable {

	/**
	 * 资源id列表
	 */
	@ApiModelProperty("更新资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 是公共
	 */
	@ApiModelProperty("是否公开")
	@NotNull
	private Boolean isPublic;
}
