package com.cyberpeace.iov.service.bo.index;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RightDemo {

	@ApiModelProperty("战术战法")
	private List<NameAndPercent> tactic;

	@ApiModelProperty("威胁类型")
	private List<NameAndPercent> threat;

	@ApiModelProperty("漏洞等级")
	private List<NameAndPercent> vuln;
}
