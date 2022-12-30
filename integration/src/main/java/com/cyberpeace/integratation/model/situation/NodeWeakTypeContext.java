package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeWeakTypeContext implements Serializable {

	@ApiModelProperty(value = "类别名称")
	private String name;

	@ApiModelProperty(value = "漏洞数量")
	private Integer count;

	public void setKeyWord(String keyWord) {
		this.name = keyWord;
	}

	public void setValueWord(Integer valueWord) {
		this.count = valueWord;
	}
}
