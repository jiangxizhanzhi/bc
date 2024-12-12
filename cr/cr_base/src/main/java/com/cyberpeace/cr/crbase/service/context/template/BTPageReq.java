package com.cyberpeace.cr.crbase.service.context.template;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * btpage请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class BTPageReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficulty;
}
