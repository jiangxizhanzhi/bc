package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分组角色信息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class GroupData {

	/**
	 * 群
	 */
	@ApiModelProperty(value = "")
	private String group;

	/**
	 * 组号
	 */
	@ApiModelProperty(value = "")
	private Integer groupNumber;

	/**
	 * 组哈希
	 */
	@ApiModelProperty(value = "")
	private Integer groupHash;

	/**
	 * 角色
	 */
	@ApiModelProperty(value = "")
	private List<GroupRole> roles;

	/**
	 * 人数
	 */
	@ApiModelProperty(value = "")
	private Integer peopleNumber;

	/**
	 * 任务编号
	 */
	@ApiModelProperty(value = "")
	private Integer missionNumber;

}
