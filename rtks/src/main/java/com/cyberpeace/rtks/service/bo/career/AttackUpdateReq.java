package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackUpdateReq implements Serializable {

	@ApiModelProperty("ATT&CK id")
	private String resourceId;

	@ApiModelProperty("场景模板id")
	private List<String> businessTemplateIds;
}
