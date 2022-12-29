package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeWeakInfoContext implements Serializable {

	@ApiModelProperty(value = "脆弱性分数")
	private Integer score;

	@ApiModelProperty(value = "脆弱性等级")
	private String degree;

	@ApiModelProperty(value = "脆弱性类别")
	private List<NodeWeakTypeContext> type;

}
