package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestSchemeIndexContext implements Serializable {

	@ApiModelProperty("测试指标名")
	private String indexName;

	@ApiModelProperty("1=工具接口；2=人工输入")
	private Integer resultGetType;

	@ApiModelProperty("选择工具接口时，选择的采集工具id")
	private String collectToolId;

	private String indexId;
}
