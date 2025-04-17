package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
@Setter
public class ActionFrameArrangeBo {

	@ApiModelProperty(value = "框架id")
	private String resourceId;

	@ApiModelProperty(value = "框架id")
	private List<ActionFrameStep> steps = Lists.newArrayList();
}
