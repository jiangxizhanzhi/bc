package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分组角色信息
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class GroupData {

	/**
	 * 组
	 */
	@ApiModelProperty(value = "")
	private String group;

	/**
	 * 组数
	 */
	@ApiModelProperty(value = "")
	private Integer groupNumber;

	/**
	 * 组id
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
