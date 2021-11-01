package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 拓扑摘要数据
 */
@Data
public class TopoDigest implements Serializable {

    /**
     * 拓扑中节点数
     */
    private Integer nodeCounts;
}
