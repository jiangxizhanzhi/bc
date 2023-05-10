package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebTheoryRecordReq extends PageReqBase {

	@ApiModelProperty("开始日期")
	private String startTime;

	@ApiModelProperty("结束日期")
	private String endTime;

	@ApiModelProperty("解决状态")
	private Integer solved;

}
