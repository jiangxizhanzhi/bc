package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.service.context.TestSchemeItemContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestSchemeContext implements Serializable {

	@ApiModelProperty("测评方案名称")
	private String schemeName;

	@ApiModelProperty("测评方案描述")
	private String schemeDesc;

	@ApiModelProperty("方案的logo id")
	private String logoId;

	private String testSchemeId;

	@ApiModelProperty("方案流程")
	private List<TestSchemeItemContext> items = Lists.newArrayList();
}
