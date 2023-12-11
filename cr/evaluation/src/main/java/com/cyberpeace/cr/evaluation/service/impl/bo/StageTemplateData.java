package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StageTemplateData implements Serializable {

	@ApiModelProperty("阶段名称")
	private String stageName;

	@ApiModelProperty("任务数据")
	private List<TaskTemplateBo> taskList;
}
