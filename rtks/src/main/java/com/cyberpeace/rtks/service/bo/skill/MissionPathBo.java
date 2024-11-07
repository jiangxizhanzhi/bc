package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务路径 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionPathBo implements Serializable {

	/**
	 * 任务模板id
	 */
	@ApiModelProperty("任务id")
	private String missionTemplateId;

	/**
	 * 完成状态
	 */
	@ApiModelProperty("任务完成状态， true：完成，false:未完成")
	private Boolean completionStatus;

	/**
	 * 相关培训
	 */
	@ApiModelProperty("关联训练")
	private List<TrainingBo> relatedTrainings;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 可用
	 */
	@ApiModelProperty("任务是否有可用场景")
	private Boolean available;
}
