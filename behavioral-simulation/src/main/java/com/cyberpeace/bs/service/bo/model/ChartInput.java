package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.common.exception.BusinessException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图表数据输入
 * 用于生成用户模型详情页面的折线图和饼图
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class ChartInput implements Serializable {

	/**
	 * 行为列表
	 */
	@ApiModelProperty(value = "行为数据列表")
	private List<Behavior> behaviors;

	/**
	 * 访问时间配置类型
	 */
	@ApiModelProperty(value = "访问时间配置类型")
	private Integer accessConfig;

	/**
	 * 访问时段开始时间
	 */
	@ApiModelProperty(value = "访问时段开始时间")
	private LocalTime startTime;

	/**
	 * 访问时段结束时间
	 */
	@ApiModelProperty(value = "访问时段结束时间")
	private LocalTime endTime;

	/**
	 * 校验开始时间和结束时间是否合理
	 */
	public void validate() {
		// 开始时间必须在结束时间之前
		if (startTime.isAfter(endTime)) {
			throw new BusinessException(BsErrorCode.TIME_RANGE_ERROR);
		}
	}
}