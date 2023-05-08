package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionTemplateRsp {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "分值")
	private Integer score;

	@ApiModelProperty(value = "技能点")
	private List<Integer> skillPoints;

	@ApiModelProperty(value = "标准答案")
	private String answer;

	@ApiModelProperty(value = "选择题选项，非选择题为\"null\"")
	private String option;

	@ApiModelProperty(value = "任务类型（单选、填空等）")
	private Integer type;

	@ApiModelProperty(value = "任务类别（resource_id）")
	private String category;

	@ApiModelProperty(value = "题解")
	private String writeUp;

}
