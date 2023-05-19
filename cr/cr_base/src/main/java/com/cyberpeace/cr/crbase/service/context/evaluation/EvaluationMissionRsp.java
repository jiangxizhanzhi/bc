package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.mission.MissionOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaluationMissionRsp implements Serializable {

	@ApiModelProperty(value = "评估id，submit_id")
	private String resourceId;

	@ApiModelProperty(value = "任务名称")
	private String title;

	@ApiModelProperty(value = "任务内容")
	private String description;

	@ApiModelProperty(value = "任务类型")
	private Integer type;

	@ApiModelProperty(value = "选择题选项")
	private List<MissionOption> option;

	@ApiModelProperty(value = "用户答案")
	private  String userAnswer;

	@ApiModelProperty(value = "标准答案")
	private String answer;

	@ApiModelProperty(value = "技能点")
	private List<Integer> skillPoints;

	@ApiModelProperty(value = "题解")
	private String writeUp;

	@ApiModelProperty(value = "满分值")
	private Integer score;
}
