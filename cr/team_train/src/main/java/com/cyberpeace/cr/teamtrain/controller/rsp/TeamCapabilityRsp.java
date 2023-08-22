package com.cyberpeace.cr.teamtrain.controller.rsp;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamCapabilityRsp implements Serializable {

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 次数
	 */
	private Integer times;
}
