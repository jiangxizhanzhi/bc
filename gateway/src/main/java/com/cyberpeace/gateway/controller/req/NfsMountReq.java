package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * NFS挂载请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class NfsMountReq implements Serializable {

	/**
	 * 主机
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty(value = "主机地址", required = true)
	private String host;

	/**
	 * 路径
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty(value = "挂载路径", required = true)
	private String path;

}
