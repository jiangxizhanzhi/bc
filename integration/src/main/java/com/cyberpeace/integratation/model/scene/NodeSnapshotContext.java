package com.cyberpeace.integratation.model.scene;

import lombok.Data;

/**
 * 镜像快照上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class NodeSnapshotContext extends SceneOpsContext {

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 快照名称
	 */
	private String snapshotName;

	/**
	 * 描述
	 */
	private String description;

}
