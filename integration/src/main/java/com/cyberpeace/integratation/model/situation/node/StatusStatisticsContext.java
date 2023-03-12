package com.cyberpeace.integratation.model.situation.node;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatusStatisticsContext implements Serializable {

	@ApiModelProperty(value = "状态")
	private String status;

	@ApiModelProperty(value = "计数")
	private Integer count;

	public void setKeyWord(String keyWord) {
		this.status = keyWord;
	}

	public void setValueWord(Integer valueWord) {
		this.count = valueWord;
	}
}
