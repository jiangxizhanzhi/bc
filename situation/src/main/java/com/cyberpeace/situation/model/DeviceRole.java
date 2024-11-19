package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 装置岗位
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DeviceRole implements Serializable {

	/**
	 * 靶标角色id
	 *
	 * @see Integer
	 */
	@ApiModelProperty("靶标角色id")
	private Integer id;

	/**
	 * 靶标角色名称
	 *
	 * @see String
	 */
	@ApiModelProperty("靶标角色名称")
	private String name;

	/**
	 * 靶标子角色
	 *
	 * @see List<DeviceRole>
	 */
	@ApiModelProperty("靶标子角色")
	private List<DeviceRole> child;

}
