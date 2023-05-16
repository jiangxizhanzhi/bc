package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练记录列表
 *
 * @author SN
 * @date 2023/05/16
 */
@Data
public class TrainingRecordListBo implements Serializable {

	@ApiModelProperty(value = "训练房间id，用于获取详情")
	private String roomId;

	@ApiModelProperty(value = "训练时间")
	private Date trainingTime;

	@ApiModelProperty(value = "序号")
	private Integer index;

	@ApiModelProperty(value = "训练时长")
	private String duration;

	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	@ApiModelProperty(value = "任务完成数")
	private Integer finished;

	@ApiModelProperty(value = "得分")
	private Integer score;

	@ApiModelProperty(value = "总分")
	private Integer totalScore;
}
