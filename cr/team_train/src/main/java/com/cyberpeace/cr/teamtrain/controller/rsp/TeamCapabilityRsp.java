package com.cyberpeace.cr.teamtrain.controller.rsp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeamCapabilityRsp implements Serializable {

	/**
	 * 角色名称
	 */
	private String roleName;

	private List<TeamCapabilityInfoRsp> list;

}
