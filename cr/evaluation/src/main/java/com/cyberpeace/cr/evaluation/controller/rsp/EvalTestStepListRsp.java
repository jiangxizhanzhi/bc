package com.cyberpeace.cr.evaluation.controller.rsp;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class EvalTestStepListRsp implements Serializable {

	@ApiModelProperty("步骤id")
	private String stepId;

	@ApiModelProperty("步骤序号")
	private Integer stage;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("过程")
	private String process;

	@ApiModelProperty("载荷列表")
	private JSONArray loadList;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("完成时间")
	private String endTime;

	@ApiModelProperty("步骤状态")
	private Integer status = EvalTaskStepContext.Status.INIT.getCode();

	@ApiModelProperty("前序步骤状态")
	private Integer preStepStatus = EvalTaskStepContext.Status.INIT.getCode();

	public void setStatus() {

		if (this.startTime == null) {
			this.status = EvalTaskStepContext.Status.INIT.getCode();
		} else if (endTime == null) {
			this.status = EvalTaskStepContext.Status.STARTED.getCode();
		} else {
			this.status = EvalTaskStepContext.Status.FINISHED.getCode();
		}

	}

	public void mergeStatus() {
		if (endTime == null){
			this.status = EvalTaskStepContext.Status.UNFINISHED.getCode();
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
