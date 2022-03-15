package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceType implements Serializable {

	@ApiModelProperty("靶标分类id")
	private Integer id;

	@ApiModelProperty("靶标一级分类")
	private Integer l1ParentId;

	@ApiModelProperty("靶标二级分类")
	private Integer l2ParentId;

	@ApiModelProperty("靶标分类名称")
	private String name;

}
