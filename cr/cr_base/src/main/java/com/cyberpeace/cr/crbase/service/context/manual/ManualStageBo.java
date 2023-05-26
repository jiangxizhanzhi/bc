package com.cyberpeace.cr.crbase.service.context.manual;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ManualStageBo implements Serializable {

	@ApiModelProperty(value = "阶段名称")
	private String stageName;

	@ApiModelProperty(value = "任务")
	private List<ManualMissionBo> missions = Lists.newArrayList();
}
