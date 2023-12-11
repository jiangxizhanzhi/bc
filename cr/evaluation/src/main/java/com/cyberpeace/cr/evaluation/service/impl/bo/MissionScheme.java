package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MissionScheme implements Serializable {

	@ApiModelProperty("颜色")
	private Integer color;

	@ApiModelProperty("模板样式")
	private Integer templateCategory;

	@ApiModelProperty("方案数据")
	private List<StageData> stageList;
}
