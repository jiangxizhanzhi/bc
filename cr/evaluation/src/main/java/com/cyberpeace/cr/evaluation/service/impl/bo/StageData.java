package com.cyberpeace.cr.evaluation.service.impl.bo;

import com.cyberpeace.cr.crbase.constant.MissionTemplateStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StageData implements Serializable {

	@ApiModelProperty("阶段名称")
	private String stageName;

	@ApiModelProperty("阶段状态")
	private Integer stageStatus = MissionTemplateStatus.NOT_START;

	@ApiModelProperty("任务数据")
	private List<TaskBo> taskList;
}
