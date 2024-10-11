package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练记录列表
 *
 * @author SN
 */
@Data
public class TrainingRecordListBo extends RecordRspBase implements Serializable {

	@ApiModelProperty(value = "训练房间id，用于获取详情")
	private String roomId;

	@ApiModelProperty(value = "序号")
	private Integer index;

	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	@ApiModelProperty(value = "任务完成数")
	private Integer finished;

	@ApiModelProperty(value = "总分")
	private Integer totalScore;
}
