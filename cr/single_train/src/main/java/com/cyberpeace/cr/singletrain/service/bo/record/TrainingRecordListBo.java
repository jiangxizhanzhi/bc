package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练记录列表
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/16
 * @see RecordRspBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingRecordListBo extends RecordRspBase implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练房间id，用于获取详情")
	private String roomId;

	/**
	 * 指数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "序号")
	private Integer index;

	/**
	 * 任务计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	/**
	 * 完成
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "任务完成数")
	private Integer finished;

	/**
	 * 总分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总分")
	private Integer totalScore;
}
