package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationStatisticsPageReq extends PageReqBase {

	@ApiModelProperty(value = "排序字段，前缀-表示倒序")
	private String sort;

	@ApiModelProperty(value = "用户id")
	private String user_id;
}
