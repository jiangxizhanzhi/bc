package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceRole implements Serializable {

	/**
	 * 靶标角色id
	 */
	@ApiModelProperty("靶标角色id")
	private Integer id;

	/**
	 * 靶标角色名称
	 */
	@ApiModelProperty("靶标角色名称")
	private String name;

	/**
	 * 靶标子角色
	 */
	@ApiModelProperty("靶标子角色")
	private List<DeviceRole> child;

}
