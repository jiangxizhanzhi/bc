package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.common.model.NameCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalTestStatisticsRsp implements Serializable {

	@ApiModelProperty("概况")
	private List<NameCount> overview;

	@ApiModelProperty("类型分布")
	private List<NameCount> categoryCount;
}
