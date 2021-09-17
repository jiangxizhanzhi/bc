package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestIndexOut extends BaseTable {

	@ApiModelProperty("指标项id")
	private String indexId;

	@ApiModelProperty("测试项id")
	private String itemId;

	@ApiModelProperty("实例id")
	private String instanceId;

	@ApiModelProperty("指标项结果（用户输入或者接口获取）")
	private String result;

}
