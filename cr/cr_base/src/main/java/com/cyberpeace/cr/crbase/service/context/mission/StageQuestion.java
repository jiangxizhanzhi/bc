package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 阶段内包含问题
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StageQuestion {

	/**
	 * 远程任务id
	 */
	@ApiModelProperty("任务id")
	private String remoteMissionId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 时间
	 */
	@ApiModelProperty("训练时间")
	private Date time;

	/**
	 * 记录
	 */
	@ApiModelProperty("训练记录")
	private String record;

	/**
	 * 任务评估状态
	 */
	@ApiModelProperty("训练结果")
	private Integer taskEvaluateStatus;

	/**
	 * 任务评估状态名称
	 */
	@ApiModelProperty("训练结果名称")
	private String taskEvaluateStatusName;
}
