package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ElementLeak implements Serializable {

	@ApiModelProperty("操作系统")
	private String operatingSystem;

	@ApiModelProperty("漏洞信息")
	private List<LeakInfo> leakList;
}
