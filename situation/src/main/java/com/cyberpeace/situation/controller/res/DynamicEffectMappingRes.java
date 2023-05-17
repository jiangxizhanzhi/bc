package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicEffectMappingRes extends DynamicEffectsMapping implements Serializable {

	@ApiModelProperty("映射类型")
	private String category;

}
