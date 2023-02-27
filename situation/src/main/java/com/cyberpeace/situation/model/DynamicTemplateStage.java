package com.cyberpeace.situation.model;

import com.cyberpeace.situation.controller.req.AttackMessageTemplateReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DynamicTemplateStage implements Serializable {

	@ApiModelProperty("阶段名称")
	private String stageName;

	@ApiModelProperty("阶段数据")
	private List<AttackMessageTemplateReq> stageData;
}
