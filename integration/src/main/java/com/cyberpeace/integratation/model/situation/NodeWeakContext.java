package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeWeakContext implements Serializable {

	@ApiModelProperty(value = "脆弱性名称")
	private String name;

	@ApiModelProperty(value = "脆弱性类别")
	private String type;

	@ApiModelProperty(value = "脆弱性描述")
	private String desc;

}
