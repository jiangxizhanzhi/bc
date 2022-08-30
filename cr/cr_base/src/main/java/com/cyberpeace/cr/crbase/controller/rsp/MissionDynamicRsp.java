package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.crbase.service.context.StatisticalInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionDynamicRsp {

	@ApiModelProperty(value = "统计信息")
	private List<StatisticalInfo> statisticalInfo;

	@ApiModelProperty(value = "动态数据")
	private List<DynamicData> dynamicData;
}
