package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.crbase.service.context.StatisticalInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务动态返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionDynamicRsp {

	@ApiModelProperty(value = "统计信息")
	private List<StatisticalInfo> statisticalInfo;

	@ApiModelProperty(value = "动态数据")
	private List<DynamicData> dynamicData;
}
