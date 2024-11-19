package com.cyberpeace.integratation.model.scene;

import lombok.Data;

/**
 * 镜像快照上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see SceneOpsContext
 * @since 1.0.0
 */
@Data
public class NodeSnapshotContext extends SceneOpsContext {

    /**
     * 节点id
     *
     * @see String
     */
    private String nodeId;

    /**
     * 快照名称
     *
     * @see String
     */
    private String snapshotName;

    /**
     * 描述
     *
     * @see String
     */
    private String description;

}
