package com.cyberpeace.integratation.model.situation.user;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserActivityContext implements Serializable {

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "活跃度")
	private Integer activity;

	public void setKeyWord(String keyWord) {
		this.userName = keyWord;
	}

	public void setValueWord(Integer valueWord) {
		this.activity = valueWord;
	}
}
