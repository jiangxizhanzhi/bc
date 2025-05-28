/**
 * 这段代码定义了一个名为 `EvalTaskStepContext` 的 Java 类，用于表示评估任务中的步骤上下文。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.context` 
 * - **作者**: SN 
 * - **日期**: 2024年11月8日 
 * ### 类属性 
 * - **stepId**: 步骤ID，类型为 `String`。 
 * - **stage**: 步骤阶段，类型为 `Integer`。 
 * - **title**: 步骤标题，类型为 `String`。 
 * - **process**: 步骤过程，类型为 `String`。 
 * - **startTime**: 步骤开始时间，类型为 `Date`。 
 * - **endTime**: 步骤结束时间，类型为 `Date`。 
 * - **status**: 步骤状态，类型为 `Integer`，默认值为 `Status.INIT.getCode()`。 
 * - **preStepStatus**: 前序步骤状态，类型为 `Integer`，默认值为 `Status.INIT.getCode()`。 
 * ### 类方法 
 * - **setResourceId(String resourceId)**: 设置步骤ID，实际上是将传入的 `resourceId` 赋值给 `stepId`。 
 * - **setStatus()**: 根据 `startTime` 和 `endTime` 的值来设置步骤状态。 
 * - **mergeStatus()**: 如果步骤没有结束，则将其状态设为 `UNFINISHED`。 
 * ### 内部枚举类 `Status` 
 * - 定义了步骤的状态枚举，包括 `INIT`, `STARTED`, `FINISHED`, `UNFINISHED`。 
 * - 每个状态都有一个对应的 `code`、中文描述 (`cnDesc`) 和英文描述 (`enDesc`)。 
 * ### 总结 
 * - `EvalTaskStepContext` 类主要用于存储和管理评估任务中的各个步骤的上下文信息。 
 * - 通过 `setStatus` 方法自动根据时间戳更新步骤状态。 
 * - 使用内部枚举类 `Status` 来管理步骤的各种状态，确保状态的一致性和可维护性。 
 * 这段代码结构清晰，注释详尽，适合用于评估任务的步骤管理和状态跟踪。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * Eval 任务步骤上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskStepContext {

	/**
	 * 步id
	 */
	@ApiModelProperty("步骤Id")
	private String stepId;

	/**
	 * 阶段
	 */
	@ApiModelProperty("步骤阶段")
	private Integer stage;

	/**
	 * 标题
	 */
	@ApiModelProperty("步骤标题")
	private String title;

	/**
	 * 过程
	 */
	@ApiModelProperty("步骤过程")
	private String process;

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private Date endTime;

	/**
	 * 状态
	 */
	@ApiModelProperty("步骤状态")
	private Integer status = Status.INIT.getCode();

	/**
	 * 前步状态
	 */
	@ApiModelProperty("前序步骤状态")
	private Integer preStepStatus = Status.INIT.getCode();

	public void setResourceId(String resourceId) {
		this.stepId = resourceId;
	}

	public void setStatus() {

		if (this.startTime == null) {
			this.status = Status.INIT.getCode();
		} else if (endTime == null) {
			this.status = Status.STARTED.getCode();
		} else {
			this.status = Status.FINISHED.getCode();
		}

	}

	public void mergeStatus() {
		if (endTime == null){
			this.status = Status.UNFINISHED.getCode();
		}
	}

	public enum Status {

		INIT(0, "未开始", ""),
		STARTED(1, "测试中", ""),
		FINISHED(2, "已完成", ""),
		UNFINISHED(3, "未完成", "");

		@Getter
		private Integer code;

		@Getter
		private String cnDesc;

		@Getter
		private String enDesc;

		Status(Integer code, String cnDesc, String enDesc) {
			this.code = code;
			this.cnDesc = cnDesc;
			this.enDesc = enDesc;
		}

	}
}
