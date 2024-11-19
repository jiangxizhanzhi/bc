package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能训练记录Bo
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/19
 */
@Data
public class SkillTrainingRecordBo implements Serializable {

	/**
     * 日期
     *
     * @see List<String>
     */
	@ApiModelProperty("日期")
	private List<String> xDates = Lists.newArrayList();

	/**
     * 完成任务数
     *
     * @see List<Integer>
     */
	@ApiModelProperty("完成任务数")
	private List<Integer> yMissions = Lists.newArrayList();

	/**
     * 任务训练记录
     *
     * @see List<MissionRecordBo>
     */
	@ApiModelProperty("任务训练记录")
	private List<MissionRecordBo> records = Lists.newArrayList();
}
