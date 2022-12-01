package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
public class EventTypeContext implements Serializable {

	@ApiModelProperty("编码")
	private String code;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("父节点")
	private String parentNode;

}
