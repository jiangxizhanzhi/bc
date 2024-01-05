package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.impl.bo.ToolBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalMissionTemplateRsp implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String name;

	@ApiModelProperty("实施步骤")
	private String step;

	@ApiModelProperty("工具")
	private List<ToolBo> tools;

	@ApiModelProperty("任务限时（分钟）")
	private Integer limitTime;

	@ApiModelProperty("验证结果 状态包含 未开始、执行、暂停、继续、中止、完成、失败 分别对应 0-6")
	private String result;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;


}
