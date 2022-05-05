package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.rtks.service.bo.skill.IdWeight;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SkillAddReq implements Serializable {

	@ApiModelProperty(value = "技能编号")
	private String resourceId;

	@ApiModelProperty(value = "技能内容")
	@NotBlank
	private String content;

	@ApiModelProperty("关联任务")
	private List<IdWeight> missions;

}
