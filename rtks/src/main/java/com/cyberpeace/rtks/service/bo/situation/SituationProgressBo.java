package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationProgressBo implements Serializable {

	@ApiModelProperty(value = "知识点")
	private KnowledgeProgressBo knowledge;

	@ApiModelProperty(value = "ATT&CK")
	private SkillProgressBo attack;

	@ApiModelProperty(value = "Engage")
	private SkillProgressBo engage;
}
