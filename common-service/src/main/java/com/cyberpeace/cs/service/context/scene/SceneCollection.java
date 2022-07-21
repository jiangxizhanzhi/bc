package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class SceneCollection implements Serializable {

	private Boolean enableFlowCollect = false;

	private Boolean enableProbeCollect;
}
