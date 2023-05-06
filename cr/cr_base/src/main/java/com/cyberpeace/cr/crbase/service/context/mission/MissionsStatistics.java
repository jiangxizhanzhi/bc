package com.cyberpeace.cr.crbase.service.context.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 任务完成比例统计
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionsStatistics {

	@ApiModelProperty(value = "完成比例")
	private Integer finishProportion;

	@ApiModelProperty(value = "未完成比例")
	private Integer notFinishProportion;

	@ApiModelProperty(value = "未提交")
	private Integer notSubmitProportion;

	@ApiModelProperty(value = "未下发")
	private Integer unpublishedProportion;

	@JsonIgnore
	private Integer missionCount;
}
