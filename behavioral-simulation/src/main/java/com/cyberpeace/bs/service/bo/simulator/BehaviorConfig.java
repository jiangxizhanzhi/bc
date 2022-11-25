package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

@Data
public class BehaviorConfig implements Serializable {

	@ApiModelProperty(value = "行为id")
	private String behaviorId;

	@ApiModelProperty(value = "行为变量列表")
	private List<VariableBo> variables = Lists.newArrayList();
}
