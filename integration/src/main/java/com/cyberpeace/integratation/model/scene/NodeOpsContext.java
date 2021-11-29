package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class NodeOpsContext extends SceneOpsContext implements Serializable {

	private String nodeId;
}