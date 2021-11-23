package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebOperationBo extends OperationBo {

	@ApiModelProperty("是否解答完成")
	private Boolean isSolved;

	@ApiModelProperty("用户提交的答案")
	private String submitAnswer;

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
