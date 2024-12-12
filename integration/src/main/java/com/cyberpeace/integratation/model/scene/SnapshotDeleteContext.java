package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

/**
 * 快照删除上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
