package com.cyberpeace.integratation.model.scene;

import lombok.Data;

/**
 * 快照删除上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see SceneOpsContext
 * @since 1.0.0
 */
@Data
public class SnapshotDeleteContext extends SceneOpsContext {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	private String nodeId;

	/**
	 * 快照id
	 *
	 * @see String
	 */
	private String snapshotId;

}
