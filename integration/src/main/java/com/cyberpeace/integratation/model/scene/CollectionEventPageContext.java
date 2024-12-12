package com.cyberpeace.integratation.model.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 收集事件分页上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CollectionEventPageContext implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("场景ID")
    private String id;

    /**
     * 节点id列表
     */
    private String nodeIds;

    /**
     * 探针id
     */// 探针id, 内置探针为001 ~ 011
    private String probeId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 分页信息, es查询时无意义
     */
    private Long page;

    /**
     * 分页数据量信息, es查询时相同`requestId`仅第一次传入有效
     */
    private Long pageSize;

    /**
     * es查询使用, 供数据量>10000时获取
     */
    private String requestId;
}
