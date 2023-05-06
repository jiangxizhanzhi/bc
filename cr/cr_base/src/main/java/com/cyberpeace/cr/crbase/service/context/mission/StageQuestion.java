package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 阶段内包含问题
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class StageQuestion {

	@ApiModelProperty("任务id")
	private String remoteMissionId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("训练时间")
	private Date time;

	@ApiModelProperty("训练记录")
	private String record;

	@ApiModelProperty("训练结果")
	private Integer taskEvaluateStatus;

	@ApiModelProperty("训练结果名称")
	private String taskEvaluateStatusName;
}
