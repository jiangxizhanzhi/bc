package com.cyberpeace.cr.evaluation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class EvalTaskStepContext extends EvalTaskStepConfig{

	@ApiModelProperty("步骤Id")
	private String stepId;

	@ApiModelProperty("步骤阶段")
	private Integer stage;

	@ApiModelProperty("步骤标题")
	private String title;

	@ApiModelProperty("步骤过程")
	private String process;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("结束时间")
	private Date endTime;

	@ApiModelProperty("步骤状态")
	private Integer status = Status.INIT.getCode();

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
