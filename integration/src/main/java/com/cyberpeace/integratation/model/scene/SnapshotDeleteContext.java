package com.cyberpeace.integratation.model.scene;

import lombok.Data;

@Data
public class SnapshotDeleteContext extends SceneOpsContext {

	private String nodeId;

	private String snapshotId;

}
