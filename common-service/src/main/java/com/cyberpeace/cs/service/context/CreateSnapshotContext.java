package com.cyberpeace.cs.service.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建快照上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CreateSnapshotContext implements Serializable {

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
