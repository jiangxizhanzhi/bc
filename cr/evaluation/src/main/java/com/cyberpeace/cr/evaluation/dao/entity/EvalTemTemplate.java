package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("cr_eval_template_template")
public class EvalTemTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("试验名称")
	private String name;

	@ApiModelProperty("试验目标")
	private String target;

	@ApiModelProperty("logoID")
	private String logoId;

	@ApiModelProperty("试验条件")
	private String expCondition;

	@ApiModelProperty("试验方法")
	private String method;

	@ApiModelProperty("测试类型")
	private Integer type;

	@ApiModelProperty("任务方案附件")
	private String scheme;

	@ApiModelProperty("拓扑id")
	private String topoId;

	@ApiModelProperty("任务方案")
	private String techniqueWarfare;

	@ApiModelProperty("创建人id")
	private String createUserId;

}
