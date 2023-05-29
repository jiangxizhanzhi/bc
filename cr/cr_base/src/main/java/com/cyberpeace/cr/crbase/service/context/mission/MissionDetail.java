package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionDetail implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("选项")
	private List<MissionOption> option;

	@ApiModelProperty(value = "技能点")
	private List<Integer> skillPoints;

	@ApiModelProperty("任务类型")
	private Integer type;

	@ApiModelProperty("任务类别id")
	private String categoryId;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("任务题解")
	private String writeUp;

	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	@ApiModelProperty("答案")
	private String answer;
}
