package com.cyberpeace.integratation.model.situation.flow;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeFlowRankContext implements Serializable {

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "节点流量")
	private Integer flowCount;

	public void setKeyWord(String keyWord) {
		this.nodeName = keyWord;
	}

	public void setValueWord(Integer valueWord) {
		this.flowCount = valueWord;
	}
}
