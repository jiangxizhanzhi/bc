package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModelContext implements Serializable {

	@ApiModelProperty("模型ID")
	private String id;

	@ApiModelProperty("模型名称")
	private String modelName;

	@ApiModelProperty("模型类型ID")
	private String modelTypeId;

	@ApiModelProperty("模型二维图片")
	private String twoDimensionPic;

	@ApiModelProperty("模型三维图片")
	private String threeDimensionPic;

}
