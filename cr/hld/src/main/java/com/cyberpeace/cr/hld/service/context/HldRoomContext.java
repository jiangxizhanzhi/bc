package com.cyberpeace.cr.hld.service.context;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import lombok.Data;

@Data
public class HldRoomContext {

	private String resourceId;

	private String roleConfig;

	private SceneConfigData sceneConfigData;

}
