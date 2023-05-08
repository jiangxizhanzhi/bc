package com.cyberpeace.cr.crbase.service.context.template;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * btpage请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class BTPageReq extends PageReqBase {

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "难度")
	private Integer difficulty;
}
