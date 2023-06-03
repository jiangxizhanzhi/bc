package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.crbase.service.context.score.RecordStatistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordDetail extends RecordRspBase implements Serializable {

	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	@ApiModelProperty(value = "训练人员")
	private String traineeName;

	@ApiModelProperty(value = "训练时长（预计）")
	private Integer durationTime;

	@ApiModelProperty(value = "训练类别")
	private Integer subType;

	private String subTypeString;

	@ApiModelProperty(value = "难度")
	private Integer difficulty;
	private String difficultyString;


	@ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	@ApiModelProperty(value = "训练描述")
	private String description;

	@ApiModelProperty(value = "训练统计")
	private RecordStatistics recordStatistics;

	@ApiModelProperty(value = "技能评分")
	private List<Integer> radars;
	private String radarsString;
	@ApiModelProperty(value = "任务详情")
	private List<RecordStageBo> stages = Lists.newArrayList();

}
