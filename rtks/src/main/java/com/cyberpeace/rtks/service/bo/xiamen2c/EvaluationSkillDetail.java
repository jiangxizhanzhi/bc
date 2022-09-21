package com.cyberpeace.rtks.service.bo.xiamen2c;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能详情
 */
@Data
public class EvaluationSkillDetail implements Serializable {

	@ApiModelProperty("技能id")
	private String skillId;

	@ApiModelProperty("技能关联任务个数")
	private Integer missionCount = 0;

	@ApiModelProperty("关联任务名称")
	private List<String> missionNames;

	@ApiModelProperty("参训人员计数")
	private Integer traineeCount = 0;

	@ApiModelProperty("参训人员得分情况")
	private List<TraineeContext> trainees;

	public EvaluationSkillDetail(String skillId) {
		this.skillId = skillId;
		this.missionNames = Lists.newArrayList();
		this.trainees = Lists.newArrayList();
	}

	public void addTrainee(String name, Integer score) {
		this.trainees.add(new TraineeContext(name, score));
	}
}

@Data
class TraineeContext {

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "得分")
	private Integer score;

	public TraineeContext(String name, Integer score) {
		this.name = name;
		this.score = score;
	}
}