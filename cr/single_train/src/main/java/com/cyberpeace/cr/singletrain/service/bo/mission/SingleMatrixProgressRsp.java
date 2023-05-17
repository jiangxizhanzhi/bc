package com.cyberpeace.cr.singletrain.service.bo.mission;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SingleMatrixProgressRsp implements Serializable {

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "用户姓名")
	private String userName;

	@ApiModelProperty(value = "用户头像")
	private String userAvatar;

	@ApiModelProperty(value = "训练是否开启")
	private Boolean started;

	@ApiModelProperty(value = "成功数")
	private Integer successful;

	@ApiModelProperty(value = "失败数")
	private Integer failed;

	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	@ApiModelProperty(value = "任务已完成数")
	private Integer finished;

	@ApiModelProperty(value = "得分")
	private Integer score;

	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	@ApiModelProperty(value = "任务矩阵")
	private List<MatrixStage> matrix = Lists.newArrayList();
}
