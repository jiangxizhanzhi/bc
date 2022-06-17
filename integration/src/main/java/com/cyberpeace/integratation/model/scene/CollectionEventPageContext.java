package com.cyberpeace.integratation.model.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CollectionEventPageContext implements Serializable {
    @ApiModelProperty("场景ID")
    private String id;

    private String nodeIds;

    // 探针id, 内置探针为001 ~ 011
    private String probeId;

    private Date startTime;

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
