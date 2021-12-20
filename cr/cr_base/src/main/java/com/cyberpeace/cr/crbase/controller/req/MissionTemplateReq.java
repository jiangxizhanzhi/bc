package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionTemplateReq implements Serializable {

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "描述", required = true)
	private String description;

	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	@ApiModelProperty(value = "技能点", required = true)
	private List<Integer> skillPoints;

	@ApiModelProperty(value = "标准答案", required = true)
	private String answer;

	@ApiModelProperty(value = "选择题选项，非选择题为\"null\"", required = true)
	private String option;

	@ApiModelProperty(value = "任务类型（单选、填空等）", required = true)
	private Integer type;

	@ApiModelProperty(value = "任务类别（密码破解、网络侦查等）", required = true)
	private String category;

	@ApiModelProperty(value = "题解", required = true)
	private String writeUp;

	@ApiModelProperty(value = "附件图片id列表")
	private List<String> images;
}
