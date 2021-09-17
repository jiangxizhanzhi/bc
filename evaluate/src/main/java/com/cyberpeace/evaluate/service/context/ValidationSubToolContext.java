package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;


import com.cyberpeace.common.model.evaluation.ToolActionModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ValidationSubToolContext implements Serializable {

	private String itemId;

	/**
	 * 测试实例id
	 */
	@ApiModelProperty("测试实例id")
	private String instanceId;

	/**
	 * 只有执行采集操作需要这个参数。子动作不需要
	 */
	@ApiModelProperty("只有执行采集操作需要这个参数。子动作不需要")
	private String indexId;

	/**
	 * 动作对应的子动作id。仅在需要执行动作的子动作时传入
	 */
	@ApiModelProperty("动作对应的子动作id。仅在需要执行动作的子动作时传入")
	private Integer subSequenceId;

	/**
	 * 对应的动作id详情。
	 * 不需要前端传入
	 */
	private ToolActionModel toolActionModel;
}
