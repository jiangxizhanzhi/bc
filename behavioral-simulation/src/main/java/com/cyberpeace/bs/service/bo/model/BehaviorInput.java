package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BehaviorInput extends BehaviorSimple implements Serializable {

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	@ApiModelProperty(value = "行为步骤")
	private Integer stepCount;
}
