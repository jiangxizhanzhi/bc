package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源认证请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("资源授权访问")
public class ResourceAuthReq {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 读认证id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("授权资源")
	private List<String> readAuthIds;

	/**
	 * 读取身份验证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer readAuthType;

}
