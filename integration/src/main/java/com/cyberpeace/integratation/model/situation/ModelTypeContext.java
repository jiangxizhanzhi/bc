package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModelTypeContext implements Serializable {

	@ApiModelProperty("类型名称")
	private String typeName;

	@ApiModelProperty("父类型ID")
	private String parentId;

	@ApiModelProperty("类型ID")
	private String id;

}
