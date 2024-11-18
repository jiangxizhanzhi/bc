package com.cyberpeace.cr.crbase.service.context.template;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * btpage请求体
 *
 * @author SN
 * @version 1.0.0
 * @see PageReqBase
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class BTPageReq extends PageReqBase {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficulty;
}
