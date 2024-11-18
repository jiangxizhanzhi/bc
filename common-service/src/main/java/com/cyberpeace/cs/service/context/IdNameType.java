package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * id名称类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class IdNameType implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "ID")
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
}
