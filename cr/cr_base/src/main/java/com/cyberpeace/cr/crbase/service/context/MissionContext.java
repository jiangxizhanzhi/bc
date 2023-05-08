package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务上下文
 * 任务模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionContext extends Mission {

	private Integer state;

	private TrainingRecord trainingRecord;

	@ApiModelProperty("单兵训练使用, 标识此任务是否已提交")
	private Boolean finished;

}
