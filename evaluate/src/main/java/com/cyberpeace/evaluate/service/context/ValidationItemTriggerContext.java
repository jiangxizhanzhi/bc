package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.model.evaluation.ToolActionModel;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具动作执行上下文
 */
@Data
public class ValidationItemTriggerContext implements Serializable {

	/**
	 * 测试项id
	 */
	@ApiModelProperty("测试项id")
	private String testItemId;

	/**
	 * 动作的输入参数
	 */
	@ApiModelProperty("动作的输入参数")
	private List<String> inputParams = Lists.newArrayList();

	/**
	 * 测评实例id
	 */
	@ApiModelProperty("测评实例id")
	private String testInstanceId;

	/**
	 * 对应的动作id详情。
	 * 这个不需要前端传入，仅作为业务处理上下文。
	 */
	private ToolActionModel toolActionModel;

	/**
	 * 父动作id
	 */
	// private String actionId;

	/**
	 * 子动作在动作列表中的编号。
	 * START = 1  # 开始
	 * SCHEDULE = 2  暂停
	 * PAUSE = 3  # 继续
	 * CONTINUE = 4  # 停止
	 * LOG = 5  # 进度查询
	 * END = 6  # 日志查询
	 * 这个不需要前端传入
	 */
	@ApiModelProperty("子动作在动作列表中的编号")
	private Integer actionSequenceIndex;

}
