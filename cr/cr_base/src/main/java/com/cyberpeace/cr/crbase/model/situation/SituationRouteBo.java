package com.cyberpeace.cr.crbase.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationRouteBo implements Serializable {

	@ApiModelProperty(value = "节点名称")
	private String node;

	@ApiModelProperty(value = "是否点亮")
	private Boolean attacked = false;
}
