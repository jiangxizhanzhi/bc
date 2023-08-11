package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RbTemplateDailyRsp {

	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	@ApiModelProperty(value = "模板id")
	private String resourceId;

	@ApiModelProperty(value = "组织")
	private List<List<JSONObject>> orgList;


}
