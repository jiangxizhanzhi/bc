package com.cyberpeace.cr.crbase.service.impl.bo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础模板基础信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class BasicTemplate {

	/**
	 * 基地信息
	 */
	@ApiModelProperty(value = "基础信息", required = true)
	@NotNull
	private BaseInfo baseInfo;
}
