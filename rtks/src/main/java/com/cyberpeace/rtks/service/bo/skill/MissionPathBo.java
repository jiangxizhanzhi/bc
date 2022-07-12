package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionPathBo implements Serializable {

	@ApiModelProperty("任务id")
	private String missionTemplateId;

	@ApiModelProperty("任务完成状态， true：完成，false:未完成")
	private Boolean completionStatus;

	@ApiModelProperty("关联训练")
	private List<TrainingBo> relatedTrainings;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("任务是否有可用场景")
	private Boolean available;
}
