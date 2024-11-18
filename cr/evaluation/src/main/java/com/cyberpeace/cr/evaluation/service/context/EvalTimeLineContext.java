package com.cyberpeace.cr.evaluation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Eval 时间线上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalTimeLineContext {

	/**
	 * 步id
	 *
	 * @see String
	 */
	@ApiModelProperty("步骤Id")
	private String stepId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("步骤标题")
	private String title;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("完成时间")
	private Date endTime;

	public void setResourceId(String resourceId) {
		this.stepId = resourceId;
	}

	public EvalTimeLineContext(String title, Date endTime) {
		this.title = title;
		this.endTime = endTime;
	}

	public EvalTimeLineContext() {}

}
