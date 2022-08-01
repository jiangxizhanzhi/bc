package com.cyberpeace.integratation.model.scene;

import lombok.Data;

/**
 * 镜像快照上下文
 */
@Data
public class NodeSnapshotContext extends SceneOpsContext {

	private String nodeId;

	private String snapshotName;

	private String description;

}
