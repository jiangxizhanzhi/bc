package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;

import lombok.Data;

/**
 * 角色
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class GroupRole {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 岗位资源id
	 */
	private String roleResourceId;

	/**
	 * 任务
	 */
	private List<RoleMissionRsp> missions;

	/**
	 * 人数
	 */
	private Integer peopleNumber;

	/**
	 * 任务编号
	 */
	private Integer missionNumber;

}