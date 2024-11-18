package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阶段内包含问题
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class StageQuestion {

	/**
	 * 远程任务id
	 *
	 * @see String
	 */
	@ApiModelProperty("任务id")
	private String remoteMissionId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("训练时间")
	private Date time;

	/**
	 * 记录
	 *
	 * @see String
	 */
	@ApiModelProperty("训练记录")
	private String record;

	/**
	 * 任务评估状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("训练结果")
	private Integer taskEvaluateStatus;

	/**
	 * 任务评估状态名称
	 *
	 * @see String
	 */
	@ApiModelProperty("训练结果名称")
	private String taskEvaluateStatusName;
}
