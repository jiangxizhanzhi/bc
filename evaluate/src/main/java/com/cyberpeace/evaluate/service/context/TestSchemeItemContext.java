package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.service.context.TestSchemeIndexContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestSchemeItemContext implements Serializable {

	@ApiModelProperty("流程名称")
	private String itemName;

	@ApiModelProperty("操作过程")
	private String operation;

	@ApiModelProperty("阶段期望")
	private String expectation;

	@ApiModelProperty("1=自动执行；0=手动执行")
	private Integer autoRun;

	@ApiModelProperty("用户选择自动执行时，动作id")
	private String actionId;

	@ApiModelProperty("选择工具时的工具id")
	private String toolId;

	@ApiModelProperty("工具的url地址，注意不是id")
	private String logoUrl;

	private String itemId;

	@ApiModelProperty("指标项")
	private List<TestSchemeIndexContext> indexes = Lists.newArrayList();
}
