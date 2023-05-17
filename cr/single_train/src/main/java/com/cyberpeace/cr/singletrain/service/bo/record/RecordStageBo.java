package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordStageBo implements Serializable {

	@ApiModelProperty(value = "阶段名称")
	private String stageName;

	@ApiModelProperty(value = "任务")
	private List<RecordMissionSimpleBo> missions = Lists.newArrayList();
}
