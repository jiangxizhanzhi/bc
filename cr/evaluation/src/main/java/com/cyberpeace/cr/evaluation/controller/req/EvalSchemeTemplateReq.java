package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.impl.bo.StageTemplateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalSchemeTemplateReq implements Serializable {

	@ApiModelProperty("技战法名称")
	private String name;

	@ApiModelProperty("技战法描述")
	private String description;

	@ApiModelProperty("技战法类型")
	private Integer type;

	@ApiModelProperty("模板类型")
	private Integer templateCategory;

	@ApiModelProperty("技战法内容")
	private List<StageTemplateData> templateData;

	@ApiModelProperty("典型攻击技战法左侧数据")
	private String[][] content;

}
