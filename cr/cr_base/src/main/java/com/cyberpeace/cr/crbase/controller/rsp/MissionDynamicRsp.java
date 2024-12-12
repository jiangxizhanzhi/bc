package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.crbase.service.context.StatisticalInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务动态响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionDynamicRsp {

	/**
	 * 统计信息
	 */
	@ApiModelProperty(value = "统计信息")
	private List<StatisticalInfo> statisticalInfo;

	/**
	 * 动态数据
	 */
	@ApiModelProperty(value = "动态数据")
	private List<DynamicData> dynamicData;
}
