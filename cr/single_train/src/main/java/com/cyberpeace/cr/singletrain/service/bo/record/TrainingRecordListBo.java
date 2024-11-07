package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练记录列表
 *
 * @author SN
 * @date 2023/05/16
 */
@Data
public class TrainingRecordListBo extends RecordRspBase implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "训练房间id，用于获取详情")
	private String roomId;

	/**
	 * 指数
	 */
	@ApiModelProperty(value = "序号")
	private Integer index;

	/**
	 * 任务计数
	 */
	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	/**
	 * 完成
	 */
	@ApiModelProperty(value = "任务完成数")
	private Integer finished;

	/**
	 * 总分
	 */
	@ApiModelProperty(value = "总分")
	private Integer totalScore;
}
