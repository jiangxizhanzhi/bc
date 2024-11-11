package com.cyberpeace.integratation.model.scene;

import lombok.Data;

/**
 * 快照删除上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SnapshotDeleteContext extends SceneOpsContext {

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 快照id
	 */
	private String snapshotId;

}
