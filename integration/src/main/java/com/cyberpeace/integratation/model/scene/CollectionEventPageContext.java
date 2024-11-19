package com.cyberpeace.integratation.model.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收集事件分页上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CollectionEventPageContext implements Serializable {
    /**
     * id
     *
     * @see String
     */
    @ApiModelProperty("场景ID")
    private String id;

    /**
     * 节点id列表
     *
     * @see String
     */
    private String nodeIds;

    /**
     * 探针id
     *
     * @see String
     */ // 探针id, 内置探针为001 ~ 011
    private String probeId;

    /**
     * 启动时间
     *
     * @see Date
     */
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    private Date endTime;

    /**
     * 分页信息, es查询时无意义
     *
     * @see Long
     */
    private Long page;

    /**
     * 分页数据量信息, es查询时相同`requestId`仅第一次传入有效
     *
     * @see Long
     */
    private Long pageSize;

    /**
     * es查询使用, 供数据量>10000时获取
     *
     * @see String
     */
    private String requestId;
}
