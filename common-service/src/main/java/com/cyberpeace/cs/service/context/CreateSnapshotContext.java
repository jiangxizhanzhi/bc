package com.cyberpeace.cs.service.context;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 创建快照上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CreateSnapshotContext implements Serializable {

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
