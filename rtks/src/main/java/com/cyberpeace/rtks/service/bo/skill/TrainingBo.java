package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingBo implements Serializable {

    /**
     * 任务id
     *
     * @see String
     */
    @ApiModelProperty(value = "任务id")
	private String missionTemplateId;

    /**
     * 任务标题
     *
     * @see String
     */
    @ApiModelProperty(value = "任务标题")
	private String missionTitle;

    /**
     * 训练id，用于跳转
     *
     * @see String
     */
    @ApiModelProperty(value = "训练id，用于跳转")
	private String trainingId;

    /**
     * 训练类型
     *
     * @see Integer
     */
    @ApiModelProperty(value = "训练类型")
	private Integer trainingType;

    /**
     * 训练名称
     *
     * @see String
     */
    @ApiModelProperty(value = "训练名称")
	private String trainingName;

    /**
     * 训练状态，(0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复
     *
     * @see Integer
     */
    @ApiModelProperty(value = "训练状态，(0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复")
	private Integer trainingStatus;

    /**
     * 训练时长
     *
     * @see Integer
     */
    @ApiModelProperty(value = "训练时长")
	private Integer durationTime;

    /**
     * 训练难度
     *
     * @see Integer
     */
    @ApiModelProperty(value = "训练难度")
	private Integer difficulty;

    /**
     * 前置技能
     *
     * @see List<String>
     */
    @ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

    /**
     * 训练技能
     *
     * @see List<String>
     */
    @ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

    /**
     * LOGO
     *
     * @see String
     */
    @ApiModelProperty(value = "LOGO")
	private String logo;

    /**
     * 当前用户角色
     *
     * @see Integer
     */
    @ApiModelProperty(value = "当前用户角色")
	private Integer roleGroup;

    /**
     * 相关技能
     *
     * @see List<SkillResBase>
     */
    @ApiModelProperty(value = "相关技能")
	private List<SkillResBase> skills;

}
