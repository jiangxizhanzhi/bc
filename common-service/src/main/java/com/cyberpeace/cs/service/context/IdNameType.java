package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * id名称类型
 *
 * @author SN
 * @date 2023/05/18
 */
@Data
public class IdNameType implements Serializable {

	@ApiModelProperty(value = "ID")
	private String id;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类型")
	private Integer type;
}
