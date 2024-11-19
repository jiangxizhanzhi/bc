package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中台收集事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpCollectionEvent implements Serializable {
    /**
     * 内容
     *
     * @see String
     */
    private String content;

    /**
     * 时间
     *
     * @see Date
     */
    private Date time;

    /**
     * 探测数据
     *
     * @see String
     */
    private String probeData;

    /**
     * 探针id
     *
     * @see String
     */
    private String probeId;

    /**
     * 节点id
     *
     * @see String
     */
    private String nodeId;

    /**
     * 场景id
     *
     * @see String
     */
    private String sceneId;
}
