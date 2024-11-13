package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论记录请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class WebTheoryRecordReq extends PageReqBase {

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始日期")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束日期")
	private String endTime;

	/**
	 * 解决
	 */
	@ApiModelProperty("解决状态")
	private Integer solved;

}
