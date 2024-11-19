package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务路径Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionPathBo implements Serializable {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty("任务id")
	private String missionTemplateId;

	/**
	 * 任务完成状态， true：完成，false:未完成
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("任务完成状态， true：完成，false:未完成")
	private Boolean completionStatus;

	/**
	 * 关联训练
	 *
	 * @see List<TrainingBo>
	 */
	@ApiModelProperty("关联训练")
	private List<TrainingBo> relatedTrainings;

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 任务是否有可用场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("任务是否有可用场景")
	private Boolean available;
}
