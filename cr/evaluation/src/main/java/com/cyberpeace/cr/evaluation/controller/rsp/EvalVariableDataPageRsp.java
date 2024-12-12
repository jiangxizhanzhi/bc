package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估变量数据分页响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalVariableDataPageRsp implements Serializable {
    /**
     * 节点ip地址
     */
    @ApiModelProperty("场景节点IP")
    private String nodeIp;

    /**
     * 收集时间
     */
    @ApiModelProperty("探针数据采集时间")
    private String collectionTime;

    /**
     * 内容
     */
    @ApiModelProperty("探针采集数据")
    private String content;
}
