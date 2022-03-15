package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategorieMap implements Serializable {

	@ApiModelProperty("角色映射")
	private Map<Integer, String> role;

	@ApiModelProperty("一级类型映射")
	private Map<Integer, String> firstCategorie;

	@ApiModelProperty("二级类型映射")
	private Map<Integer, String> secondCategorie;

}
