package com.cyberpeace.cr.crbase.service.context.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务完成比例统计
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MissionsStatistics {

	/**
	 * 完成比例
	 */
	@ApiModelProperty(value = "完成比例")
	private Integer finishProportion;

	/**
	 * 未完成比例
	 */
	@ApiModelProperty(value = "未完成比例")
	private Integer notFinishProportion;

	/**
	 * 不提交比例
	 */
	@ApiModelProperty(value = "未提交")
	private Integer notSubmitProportion;

	/**
	 * 未发布比例
	 */
	@ApiModelProperty(value = "未下发")
	private Integer unpublishedProportion;

	/**
	 * 任务计数
	 */
	@JsonIgnore
	private Integer missionCount;
}
