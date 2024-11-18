package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分组角色信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class GroupData {

	/**
	 * 群
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "")
	private String group;

	/**
	 * 组号
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "")
	private Integer groupNumber;

	/**
	 * 组哈希
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "")
	private Integer groupHash;

	/**
	 * 角色
	 *
	 * @see List<GroupRole>
	 */
	@ApiModelProperty(value = "")
	private List<GroupRole> roles;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "")
	private Integer peopleNumber;

	/**
	 * 任务编号
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "")
	private Integer missionNumber;

}
