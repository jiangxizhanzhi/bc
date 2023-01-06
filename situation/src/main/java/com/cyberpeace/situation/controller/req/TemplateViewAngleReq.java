package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplateViewAngleReq implements Serializable {

	@ApiModelProperty("模板ID")
	private String templateId;

	@ApiModelProperty("类型 1-模板 2-实例")
	private Integer type;

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

}
