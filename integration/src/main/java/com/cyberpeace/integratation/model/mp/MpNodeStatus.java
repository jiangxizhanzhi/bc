package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 中台节点状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpNodeStatus implements Serializable {

    /**
     * 节点Id
     *
     * @see String
     */
    private String nodeId;

    /**
     * 节点状态
     *
     * @see Integer
     */
    private Integer status;

    /**
     * 节点连线列表
     *
     * @see List<MpConnectionStatus>
     */
    private List<MpConnectionStatus> connectionStatusList;

}
