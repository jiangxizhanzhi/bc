package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordMissionSimpleBo implements Serializable {

	@ApiModelProperty(value = "任务名称")
	private String title;

	@ApiModelProperty(value = "任务描述")
	private String description;

	@ApiModelProperty(value = "选项")
	private List<JSONObject> option;

	@ApiModelProperty(value = "任务答案")
	private String answer;

	@ApiModelProperty(value = "用户答案")
	private String userAnswer;

	@ApiModelProperty(value = "任务类型")
	private Integer type;

	@ApiModelProperty(value = "是否作答")
	private Boolean answered;

}
