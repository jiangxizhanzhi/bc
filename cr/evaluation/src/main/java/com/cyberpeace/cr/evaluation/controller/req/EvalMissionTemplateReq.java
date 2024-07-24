package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.impl.bo.ToolBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class EvalMissionTemplateReq implements Serializable {

	@ApiModelProperty("任务名称")
	@NotNull
	private String name;

	@ApiModelProperty("实施步骤")
	private String step;

	@ApiModelProperty("工具")
	private List<ToolBo> tools;

	@ApiModelProperty("任务限时（分钟）")
	private Integer limitTime;

	@ApiModelProperty("验证结果")
	private String result;

	/**
	 * 0：未开始；1：执行；2：暂停；3：继续；
	 * 4：中止；5：失败；6：完成；7：超时
	 */
	@ApiModelProperty("任务状态")
	private Integer status;
}
