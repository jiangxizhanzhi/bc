package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingBo implements Serializable {

	/**
	 * 任务模板id
	 */
	@ApiModelProperty(value = "任务id")
	private String missionTemplateId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务标题")
	private String missionTitle;

	/**
	 * 训练id
	 */
	@ApiModelProperty(value = "训练id，用于跳转")
	private String trainingId;

	/**
	 * 训练类型
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer trainingType;

	/**
	 * 训练名称
	 */
	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	/**
	 * 训练状态
	 */
	@ApiModelProperty(value = "训练状态，(0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复")
	private Integer trainingStatus;

	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "训练时长")
	private Integer durationTime;

	/**
	 * 困难
	 */
	@ApiModelProperty(value = "训练难度")
	private Integer difficulty;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "当前用户角色")
	private Integer roleGroup;

	/**
	 * 技能
	 */
	@ApiModelProperty(value = "相关技能")
	private List<SkillResBase> skills;

//	@ApiModelProperty(value = "是否可用")
//	private Boolean available;
}
