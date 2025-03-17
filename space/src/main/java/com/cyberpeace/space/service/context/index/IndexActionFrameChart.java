package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
@Setter
public class IndexActionFrameChart {

	private List<String> xDates = Lists.newArrayList();

	@ApiModelProperty(value = "面向攻击流程")
	private List<Integer> process = Lists.newArrayList();

	@ApiModelProperty(value = "面向攻击目标")
	private List<Integer> target = Lists.newArrayList();

}
