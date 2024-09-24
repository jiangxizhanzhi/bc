package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.dao.mode.Plugins;
import com.cyberpeace.cr.evaluation.dao.mode.SceneTopology;
import lombok.Data;

@Data
public class EvalSceneMapRsp {

	private SceneTopology sceneTopology;
	private Plugins plugins;

}
