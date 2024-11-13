package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模型
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class MissionContext extends Mission {

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 状态字符串
	 */
	private String stateString;

	/**
	 * 训练记录
	 */
	private TrainingRecord trainingRecord;

	/**
	 * 完成
	 */
	@ApiModelProperty("单兵训练使用, 标识此任务是否已提交")
	private Boolean finished;

}
