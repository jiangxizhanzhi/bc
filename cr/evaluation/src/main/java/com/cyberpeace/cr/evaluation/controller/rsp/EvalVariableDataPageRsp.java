package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估变量数据分页响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalVariableDataPageRsp implements Serializable {
    /**
     * 节点ip地址
     *
     * @see String
     */
    @ApiModelProperty("场景节点IP")
    private String nodeIp;

    /**
     * 收集时间
     *
     * @see String
     */
    @ApiModelProperty("探针数据采集时间")
    private String collectionTime;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty("探针采集数据")
    private String content;
}
