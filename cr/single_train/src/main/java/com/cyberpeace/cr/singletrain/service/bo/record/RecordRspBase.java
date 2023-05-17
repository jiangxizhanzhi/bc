package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordRspBase implements Serializable {

	@ApiModelProperty(value = "训练时间")
	private Date trainingTime;

	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	@ApiModelProperty(value = "得分")
	private Integer score;
}
