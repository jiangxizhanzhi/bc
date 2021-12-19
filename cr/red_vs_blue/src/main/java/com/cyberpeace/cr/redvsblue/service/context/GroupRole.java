package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;

import lombok.Data;

@Data
public class GroupRole {

	private String name;

	private String roleResourceId;

	private List<RoleMissionRsp> missions;

	private Integer peopleNumber;

	private Integer missionNumber;

}