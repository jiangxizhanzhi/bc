package com.cyberpeace.rtks.service.bo.xiamen2c;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人员评估-技能
 */
@Data
public class EvaluationSkillBo implements Serializable {

	@ApiModelProperty(value = "技能id")
	private String skillId;

	@ApiModelProperty(value = "技能名称")
	private String skillName;

	@ApiModelProperty(value = "岗位id")
	private String roleId;

	@ApiModelProperty(value = "岗位名称")
	private String roleName;

	@ApiModelProperty(value = "技能分值")
	private Integer skillFullScore = 100;

	@ApiModelProperty(value = "技能关联任务数量")
	private Integer missionCount = 0;

	@ApiModelProperty(value = "技能已参训人员")
	private Integer traineeCount = 0;

}
