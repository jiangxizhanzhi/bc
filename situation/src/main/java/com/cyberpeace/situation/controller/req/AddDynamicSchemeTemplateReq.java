package com.cyberpeace.situation.controller.req;

import com.cyberpeace.situation.model.DynamicTemplateStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddDynamicSchemeTemplateReq implements Serializable {

	@ApiModelProperty("方案名称")
	private String schemeName;

	@ApiModelProperty("方案数据")
	private List<DynamicTemplateStage> schemeData;
}
