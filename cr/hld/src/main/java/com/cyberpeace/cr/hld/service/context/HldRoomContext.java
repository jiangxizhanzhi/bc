package com.cyberpeace.cr.hld.service.context;

import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;

import lombok.Data;

@Data
public class HldRoomContext {

	private String taskId;

	private String resourceId;

	private String roleConfig;

	private BaseInfo baseInfo;

}
