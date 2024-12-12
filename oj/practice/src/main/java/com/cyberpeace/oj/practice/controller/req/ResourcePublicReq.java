package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 资源公共请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ResourcePublicReq implements Serializable {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("更新资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	@NotNull
	private Boolean isPublic;
}
