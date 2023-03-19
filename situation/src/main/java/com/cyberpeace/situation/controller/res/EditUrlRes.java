package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditUrlRes implements Serializable {

	@ApiModelProperty("可视化编辑链接")
	private String url;

	public EditUrlRes(String modelId) {
		this.url = "http://172.30.104.8:2020/";
	}

}
