package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebOperationBo extends OperationBo {

	private PracticeNoSolvedBo resolvedDetail;

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
