package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SituationEnumRes implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("code")
	private Integer code;

	public SituationEnumRes(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
