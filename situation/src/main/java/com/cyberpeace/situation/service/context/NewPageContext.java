package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewPageContext implements Serializable {

	@ApiModelProperty(value = "消息类型")
	private String type = "new";

	@ApiModelProperty(value = "跳转链接", required = true)
	private String url;

	public NewPageContext() {}

	public NewPageContext(String code) {
		this.url = "page/cr/situation/screen/" + code;
	}

}
