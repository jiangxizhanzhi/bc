package com.cyberpeace.ai.model.vo.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("任务完成情况")
public class TrainListDataVO {

	@ApiModelProperty("训练名称")
	private String name;

	@ApiModelProperty("任务类型")
	private String type;

	@ApiModelProperty("总任务数")
	private Integer total;

	@ApiModelProperty("已完成任务数")
	private Integer completed;

	@ApiModelProperty("完成率")
	private String completionRate;

}