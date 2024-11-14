package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillTrainingRecordBo implements Serializable {

	/**
	 * 日期
	 */
	@ApiModelProperty("日期")
	private List<String> xDates = Lists.newArrayList();

	/**
	 * 完成任务数
	 */
	@ApiModelProperty("完成任务数")
	private List<Integer> yMissions = Lists.newArrayList();

	/**
	 * 任务训练记录
	 */
	@ApiModelProperty("任务训练记录")
	private List<MissionRecordBo> records = Lists.newArrayList();
}
