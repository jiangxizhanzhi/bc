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
 * @date 2024/11/11
 */
@Data
@ApiModel("资源授权访问")
public class ResourceAuthReq {

	/**
	 * 资源id列表
	 */
	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 读认证id列表
	 */
	@ApiModelProperty("授权资源")
	private List<String> readAuthIds;

	/**
	 * 读取身份验证类型
	 */
	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer readAuthType;

}
