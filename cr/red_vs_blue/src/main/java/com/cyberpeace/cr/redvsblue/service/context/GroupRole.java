package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;

import lombok.Data;

/**
 * 角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class GroupRole {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 角色资源id
	 *
	 * @see String
	 */
	private String roleResourceId;

	/**
	 * 任务
	 *
	 * @see List<RoleMissionRsp>
	 */
	private List<RoleMissionRsp> missions;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	private Integer peopleNumber;

	/**
	 * 任务编号
	 *
	 * @see Integer
	 */
	private Integer missionNumber;

}