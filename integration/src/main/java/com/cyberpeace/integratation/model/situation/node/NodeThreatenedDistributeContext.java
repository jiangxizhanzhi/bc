package com.cyberpeace.integratation.model.situation.node;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeThreatenedDistributeContext implements Serializable {

	@ApiModelProperty(value = "受威胁程度")
	private String level;

	@ApiModelProperty(value = "计数")
	private Integer count;

	public void setKeyWord(String keyWord) {
		this.level = keyWord;
	}

	public void setValueWord(Integer valueWord) {
		this.count = valueWord;
	}
}
