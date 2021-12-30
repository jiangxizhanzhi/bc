package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoomRoleContext implements Serializable {

	private String roomId;

	private Integer roleGroup;
}
