package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillTrainingRecordBo implements Serializable {

	@ApiModelProperty("日期")
	List<String> xDates = Lists.newArrayList();

	@ApiModelProperty("完成任务数")
	List<Integer> yMissions = Lists.newArrayList();

	@ApiModelProperty("任务训练记录")
	List<MissionRecordBo> records = Lists.newArrayList();
}
