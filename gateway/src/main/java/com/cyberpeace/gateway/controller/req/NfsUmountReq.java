package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * NFSumount请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class NfsUmountReq implements Serializable {

	/**
	 * 路径
	 */
	@NotBlank
	@ApiModelProperty(value = "卸载路径", required = true)
	private String path;

}
