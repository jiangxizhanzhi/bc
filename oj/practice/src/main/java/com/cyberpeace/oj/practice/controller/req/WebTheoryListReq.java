package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class WebTheoryListReq extends AdminTheoryListReq {

	/**
	 * 解决
	 */
	@ApiModelProperty("解决状态")
	private Integer solved;

}
