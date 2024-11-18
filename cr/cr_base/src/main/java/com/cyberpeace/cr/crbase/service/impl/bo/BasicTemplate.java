package com.cyberpeace.cr.crbase.service.impl.bo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础模板基础信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class BasicTemplate {

	/**
	 * 基地信息
	 *
	 * @see BaseInfo
	 */
	@ApiModelProperty(value = "基础信息", required = true)
	@NotNull
	private BaseInfo baseInfo;
}
