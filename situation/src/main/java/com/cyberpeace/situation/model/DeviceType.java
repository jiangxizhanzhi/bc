package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DeviceType implements Serializable {

	/**
	 * 靶标分类id
	 *
	 * @see Integer
	 */
	@ApiModelProperty("靶标分类id")
	private Integer id;

	/**
	 * 靶标一级分类
	 *
	 * @see Integer
	 */
	@ApiModelProperty("靶标一级分类")
	private Integer l1ParentId;

	/**
	 * 靶标二级分类
	 *
	 * @see Integer
	 */
	@ApiModelProperty("靶标二级分类")
	private Integer l2ParentId;

	/**
	 * 靶标分类名称
	 *
	 * @see String
	 */
	@ApiModelProperty("靶标分类名称")
	private String name;

}
