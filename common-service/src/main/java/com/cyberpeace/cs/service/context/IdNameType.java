package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * id名称类型
 *
 * @author SN
 * @date 2023/05/18
 */
@Getter
@Setter
public class IdNameType implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "ID")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
}
