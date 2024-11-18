package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Mission
 * @since 1.0.0
 */
@Data
public class MissionContext extends Mission {

	/**
	 * 州
	 *
	 * @see Integer
	 */
	private Integer state;
	/**
	 * 状态字符串
	 *
	 * @see String
	 */
	private String stateString;

	/**
	 * 训练记录
	 *
	 * @see TrainingRecord
	 */
	private TrainingRecord trainingRecord;

	/**
	 * 完成
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("单兵训练使用, 标识此任务是否已提交")
	private Boolean finished;

}
