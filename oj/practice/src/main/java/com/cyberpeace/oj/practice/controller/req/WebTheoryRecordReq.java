package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论记录请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class WebTheoryRecordReq extends PageReqBase {

	/**
	 * 开始时间
	 *
	 * @see String
	 */
	@ApiModelProperty("开始日期")
	private String startTime;

	/**
	 * 结束时间
	 *
	 * @see String
	 */
	@ApiModelProperty("结束日期")
	private String endTime;

	/**
	 * 解决
	 *
	 * @see Integer
	 */
	@ApiModelProperty("解决状态")
	private Integer solved;

}
