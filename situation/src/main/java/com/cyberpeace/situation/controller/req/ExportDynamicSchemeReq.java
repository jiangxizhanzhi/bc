package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效方案请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ExportDynamicSchemeReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "动效方案id", required = true)
	@NotBlank
	private String resourceId;
}
