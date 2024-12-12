package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 中台节点状态
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpNodeStatus implements Serializable {

    /**
     * 节点Id
     */
    private String nodeId;

    /**
     * 节点状态
     */
    private Integer status;

    /**
     * 节点连线列表
     */
    private List<MpConnectionStatus> connectionStatusList;

}
