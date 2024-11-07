package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能训练记录博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillTrainingRecordBo implements Serializable {

	/**
	 * x 日期
	 */
	@ApiModelProperty("日期")
	List<String> xDates = Lists.newArrayList();

	/**
	 * Y 任务
	 */
	@ApiModelProperty("完成任务数")
	List<Integer> yMissions = Lists.newArrayList();

	/**
	 * 记录
	 */
	@ApiModelProperty("任务训练记录")
	List<MissionRecordBo> records = Lists.newArrayList();
}
