package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainingBo implements Serializable {

	@ApiModelProperty(value = "任务id")
	private String missionTemplateId;

	@ApiModelProperty(value = "任务标题")
	private String missionTitle;

	@ApiModelProperty(value = "训练id，用于跳转")
	private String trainingId;

	@ApiModelProperty(value = "训练类型")
	private Integer trainingType;

	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	@ApiModelProperty(value = "训练状态，(0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复")
	private Integer trainingStatus;

	@ApiModelProperty(value = "训练时长")
	private Integer durationTime;

	@ApiModelProperty(value = "训练难度")
	private Integer difficulty;

	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	@ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	@ApiModelProperty(value = "当前用户角色")
	private Integer roleGroup;

	@ApiModelProperty(value = "相关技能")
	private List<SkillResBase> skills;

}
