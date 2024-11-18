package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.crbase.service.context.StatisticalInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务动态响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionDynamicRsp {

	/**
	 * 统计信息
	 *
	 * @see List<StatisticalInfo>
	 */
	@ApiModelProperty(value = "统计信息")
	private List<StatisticalInfo> statisticalInfo;

	/**
	 * 动态数据
	 *
	 * @see List<DynamicData>
	 */
	@ApiModelProperty(value = "动态数据")
	private List<DynamicData> dynamicData;
}
