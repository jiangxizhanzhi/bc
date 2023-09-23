package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模型
 */
@Data
public class MissionContext extends Mission {

	private Integer state;

	private String stateString;

	private TrainingRecord trainingRecord;

	@ApiModelProperty("单兵训练使用, 标识此任务是否已提交")
	private Boolean finished;

}
