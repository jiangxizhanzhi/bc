package com.cyberpeace.cr.evaluation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评时间线上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTimeLineContext {

	/**
	 * 步骤Id
	 */
	@ApiModelProperty("步骤Id")
	private String stepId;

	/**
	 * 步骤标题
	 */
	@ApiModelProperty("步骤标题")
	private String title;

	/**
	 * 完成时间
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
