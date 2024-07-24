package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cr_eval_mission_template")
public class EvalMissionTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String name;

	@ApiModelProperty("实施步骤")
	private String step;

	@ApiModelProperty("工具")
	private String tools;

	@ApiModelProperty("任务限时（分钟）")
	private Integer limitTime;

	@ApiModelProperty("剩余时间")
	private Long remainingTime;

	@ApiModelProperty("验证结果")
	private String result;

	@ApiModelProperty("任务id")
	private String taskId;

	@ApiModelProperty("任务状态，默认未开始")
	private Integer status;

	@ApiModelProperty("结束时间")
	private Date endTime;
}
