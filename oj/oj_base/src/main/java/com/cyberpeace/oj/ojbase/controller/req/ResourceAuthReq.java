package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源认证请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("资源授权访问")
public class ResourceAuthReq {

	/**
	 * 资源ID
	 */
	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 授权资源
	 */
	@ApiModelProperty("授权资源")
	private List<String> readAuthIds;

	/**
	 * 授权模式, 1-所有, 2-自定义
	 */
	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer readAuthType;

}
