package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.service.bo.variable.VariableSimple;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BehaviorWithVariable implements Serializable {

	@ApiModelProperty(value = "行为id")
	private String id;

	@ApiModelProperty(value = "行为名称")
	private String name;

	@ApiModelProperty(value = "标签")
	private List<String> tag = Lists.newArrayList();

	@ApiModelProperty(value = "行为步骤")
	private Integer stepCount;

	@ApiModelProperty(value = "行为变量（默认值）")
	private List<VariableSimple> variables = Lists.newArrayList();
}
