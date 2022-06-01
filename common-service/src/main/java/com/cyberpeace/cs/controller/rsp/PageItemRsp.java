package com.cyberpeace.cs.controller.rsp;

import com.cyberpeace.common.model.CommonContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class PageItemRsp {

	@JsonIgnore
	private String enName;

	@JsonIgnore
	private String cnName;

	@ApiModelProperty("配置项名称")
	private String name;

	@ApiModelProperty("配置项值")
	private String value;

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getName() {
		return CommonContext.isChinese() ? cnName : enName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
