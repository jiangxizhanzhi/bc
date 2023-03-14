package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class EditUrlRes implements Serializable {

	@ApiModelProperty("可视化编辑链接")
	private String url;

	public EditUrlRes(String modelId) {
		this.url = "http://www.baidu.com/";
	}

}
