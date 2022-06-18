package com.cyberpeace.cr.hld.service.context;

import lombok.Data;

@Data
public class HldRoomContext {

	private String taskId;

	private String resourceId;

	private String roleConfig;

	private HldBaseInfo baseInfo;

}
