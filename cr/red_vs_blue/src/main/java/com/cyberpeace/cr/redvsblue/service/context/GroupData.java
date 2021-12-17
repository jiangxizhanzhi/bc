package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import lombok.Data;

@Data
public class GroupData {

	private String group;

	private Integer groupNumber;

	private Integer groupHash;

	private List<RoomRole> roles;

	private Integer peopleNumber;

	private Integer missionNumber;

}
