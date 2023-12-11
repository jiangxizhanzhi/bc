package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

	@ApiModelProperty("验证结果")
	private String result;
}
