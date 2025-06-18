package com.cyberpeace.ai.model.vo.train;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("任务完成情况")
public class TrainListDataVO {

	private String resourceId;

	@ApiModelProperty("训练名称")
	private String name;

	@ApiModelProperty("任务类型")
	private String type;

	@ApiModelProperty("总任务数")
	private Integer total = 0;

	@ApiModelProperty("已完成任务数")
	private Integer completed = 0;

	@ApiModelProperty("完成率")
	private Integer completionRate = 0;

	private Date startTime;

}