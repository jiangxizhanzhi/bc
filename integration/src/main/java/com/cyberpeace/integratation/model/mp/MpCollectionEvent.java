package com.cyberpeace.integratation.model.mp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 中台收集事件
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpCollectionEvent implements Serializable {
    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private Date time;

    /**
     * 探测数据
     */
    private String probeData;

    /**
     * 探针id
     */
    private String probeId;

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 场景id
     */
    private String sceneId;
}
